package com.ondam.seller.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.ondam.common.ProjectWebappPaths;
import com.ondam.common.controller.Controller;
import com.ondam.seller.dao.SellerDAO;
import com.ondam.seller.dao.VendorDAO;
import com.ondam.seller.dto.SellerDTO;
import com.ondam.seller.dto.VendorDTO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * 판매자 설정 — GET /seller/settings, POST /seller/settings/save, POST /seller/settings/logo
 */
public class SellerSettingsController implements Controller {

	private static final String CTX_PARAM_LOGO_DIR = "sellerLogoStorageDirectory";

	private final VendorDAO vendorDAO = new VendorDAO();
	private final SellerDAO sellerDAO = new SellerDAO();

	private static File resolveLogoStorageRoot(ServletContext ctx) {
		String cfg = ctx.getInitParameter(CTX_PARAM_LOGO_DIR);
		if (cfg != null) {
			String t = cfg.trim();
			if (!t.isEmpty()) {
				return new File(t);
			}
		}
		return ProjectWebappPaths.sellerLogoDirectory(ctx);
	}

	static final class AddrParts {
		String zip = "";
		String addr1 = "";
		String addr2 = "";
	}

	/** 가입 시와 동일: {@code (우편) 기본주소 상세주소} */
	static AddrParts parseStoredAddress(String combined) {
		AddrParts p = new AddrParts();
		if (combined == null) {
			return p;
		}
		String s = combined.trim();
		if (s.isEmpty()) {
			return p;
		}
		if (s.startsWith("(")) {
			int close = s.indexOf(')');
			if (close > 1 && close <= 8) {
				String inner = s.substring(1, close).trim();
				if (inner.matches("\\d{5}")) {
					p.zip = inner;
					String rest = s.substring(close + 1).trim();
					int sp = rest.lastIndexOf(' ');
					if (sp > 0) {
						p.addr1 = rest.substring(0, sp).trim();
						p.addr2 = rest.substring(sp + 1).trim();
					} else {
						p.addr1 = rest;
					}
					return p;
				}
			}
		}
		int sp = s.lastIndexOf(' ');
		if (sp > 0) {
			p.addr1 = s.substring(0, sp).trim();
			p.addr2 = s.substring(sp + 1).trim();
		} else {
			p.addr1 = s;
		}
		return p;
	}

	static String combineAddress(String zip, String addr1, String addr2) {
		String z = zip != null ? zip.trim() : "";
		String a1 = addr1 != null ? addr1.trim() : "";
		String a2 = addr2 != null ? addr2.trim() : "";
		return "(" + z + ") " + a1 + " " + a2;
	}

	private static Integer parseIntParam(String s) {
		if (s == null) {
			return null;
		}
		s = s.trim();
		if (s.isEmpty()) {
			return null;
		}
		try {
			return Integer.valueOf(Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());
		String method = request.getMethod();

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginSeller") == null) {
			return "redirect:/seller/auth";
		}
		SellerDTO seller = (SellerDTO) session.getAttribute("loginSeller");
		int vendorNo = seller.getVendorNo();

		if ("/seller/settings/logo".equals(path) && "POST".equalsIgnoreCase(method)) {
			return handleLogoUpload(request, vendorNo);
		}

		if ("/seller/settings/save".equals(path) && "POST".equalsIgnoreCase(method)) {
			return handleSettingsSave(request, session, seller, vendorNo);
		}

		if ("/seller/settings".equals(path) && "GET".equalsIgnoreCase(method)) {
			populateSettingsPage(request, seller, vendorNo);
			return "seller/settings";
		}

		return "redirect:/seller/settings";
	}

	private void populateSettingsPage(HttpServletRequest request, SellerDTO seller, int vendorNo) {
		VendorDTO vendor = vendorDAO.getVendorByVendorNo(vendorNo);
		if (vendor == null) {
			request.setAttribute("vendorLoadError", Boolean.TRUE);
			request.setAttribute("sellerPageTitle", "설정");
			request.setAttribute("sellerActiveMenu", "setting");
			return;
		}

		String logo = vendor.getLogoImg();
		if (logo != null) {
			logo = logo.trim();
			if (logo.isEmpty()) {
				logo = null;
			}
		}
		request.setAttribute("vendor", vendor);
		request.setAttribute("managerName", seller.getSellerName());
		request.setAttribute("vendorLogoFile", logo);
		request.setAttribute("vendorLogoVendorNo", Integer.valueOf(vendorNo));
		attachVendorLogoImgSrc(request, vendorNo, logo);

		AddrParts ship = parseStoredAddress(vendor.getBizAddr());
		AddrParts ret = parseStoredAddress(vendor.getBizReturnAddr());
		request.setAttribute("shipZip", ship.zip);
		request.setAttribute("shipAddr1", ship.addr1);
		request.setAttribute("shipAddr2", ship.addr2);
		request.setAttribute("returnZip", ret.zip);
		request.setAttribute("returnAddr1", ret.addr1);
		request.setAttribute("returnAddr2", ret.addr2);

		String ba = vendor.getBizAddr();
		String br = vendor.getBizReturnAddr();
		boolean sameReturn = ba != null && br != null && ba.trim().equals(br.trim());
		request.setAttribute("sameReturnAddr", Boolean.valueOf(sameReturn));

		request.setAttribute("sellerPageTitle", "설정");
		request.setAttribute("sellerActiveMenu", "setting");
	}

	private String handleSettingsSave(HttpServletRequest request, HttpSession session, SellerDTO seller, int vendorNo) {
		VendorDTO current = vendorDAO.getVendorByVendorNo(vendorNo);
		if (current == null) {
			return "redirect:/seller/settings?save=fail";
		}

		String storeName = trimToNull(request.getParameter("storeName"));
		String managerName = trimToNull(request.getParameter("managerName"));
		String csPhone = trimToNull(request.getParameter("csPhone"));
		String csEmail = trimToNull(request.getParameter("csEmail"));
		String bizNo = request.getParameter("bizNo");
		String storeIntro = request.getParameter("storeIntro");
		String returnGuide = request.getParameter("returnGuide");

		String shipZip = request.getParameter("shipZip");
		String shipAddr1 = request.getParameter("shipAddr1");
		String shipAddr2 = request.getParameter("shipAddr2");
		String returnZip = request.getParameter("returnZip");
		String returnAddr1 = request.getParameter("returnAddr1");
		String returnAddr2 = request.getParameter("returnAddr2");

		boolean sameReturn = request.getParameter("sameReturnAddr") != null;
		String bizAddr = combineAddress(shipZip, shipAddr1, shipAddr2);
		String bizReturnAddr = sameReturn ? bizAddr : combineAddress(returnZip, returnAddr1, returnAddr2);

		Integer shipFee = parseIntParam(request.getParameter("shipFee"));
		Integer freeShipMin = parseIntParam(request.getParameter("freeOver"));
		String prepDays = request.getParameter("prepDays");
		String courier = request.getParameter("courier");
		Integer islandExtra = parseIntParam(request.getParameter("islandExtra"));
		String shipNotice = request.getParameter("shipNotice");
		String delayNotice = request.getParameter("delayMsg");
		String giftNotice = request.getParameter("giftMsg");
		String exchangeNotice = request.getParameter("exchangeMsg");

		if (storeName == null || managerName == null || csPhone == null || csEmail == null) {
			return "redirect:/seller/settings?save=fail";
		}
		if (!csEmail.contains("@")) {
			return "redirect:/seller/settings?save=fail";
		}
		if (shipFee == null || freeShipMin == null) {
			return "redirect:/seller/settings?save=fail";
		}

		VendorDTO dto = new VendorDTO();
		dto.setVendorName(storeName);
		dto.setBizRegNo(bizNo != null ? bizNo.trim() : null);
		dto.setBizTel(csPhone);
		dto.setContactEmail(csEmail);
		dto.setBizAddr(bizAddr);
		dto.setBizReturnAddr(bizReturnAddr);
		dto.setBizDescription(storeIntro);
		dto.setReturnExchangeGuide(returnGuide);
		dto.setShipFee(shipFee);
		dto.setFreeShipMin(freeShipMin);
		dto.setPrepDays(prepDays != null ? prepDays.trim() : null);
		dto.setDefaultCourier(courier != null ? courier.trim() : null);
		dto.setIslandExtra(islandExtra);
		dto.setShipNotice(shipNotice);
		dto.setDelayNotice(delayNotice);
		dto.setGiftNotice(giftNotice);
		dto.setExchangeNotice(exchangeNotice);

		if (!vendorDAO.updateSellerSettings(dto, vendorNo)) {
			return "redirect:/seller/settings?save=fail";
		}
		if (!sellerDAO.updateSellerName(seller.getSellerAccountNo(), managerName)) {
			return "redirect:/seller/settings?save=fail";
		}

		seller.setSellerName(managerName);
		session.setAttribute("loginSeller", seller);
		session.setAttribute("vendorName", storeName);

		return "redirect:/seller/settings?save=ok";
	}

	private static String trimToNull(String s) {
		if (s == null) {
			return null;
		}
		s = s.trim();
		return s.isEmpty() ? null : s;
	}

	private void attachVendorLogoImgSrc(HttpServletRequest request, int vendorNo, String logoFileName) {
		if (logoFileName == null || logoFileName.isEmpty()) {
			return;
		}
		File realRoot = resolveLogoStorageRoot(request.getServletContext());
		File inVendorDir = new File(new File(realRoot, String.valueOf(vendorNo)), logoFileName);
		File flat = new File(realRoot, logoFileName);
		String ctx = request.getContextPath();
		if (inVendorDir.isFile()) {
			request.setAttribute("vendorLogoImgSrc",
					ctx + "/images/seller/logo/" + vendorNo + "/" + urlEncodePathSegment(logoFileName));
		} else if (flat.isFile()) {
			request.setAttribute("vendorLogoImgSrc",
					ctx + "/images/seller/logo/" + urlEncodePathSegment(logoFileName));
		} else {
			String legacyRp = request.getServletContext().getRealPath("/images/seller/logo");
			if (legacyRp != null) {
				File legacyFlat = new File(legacyRp, logoFileName);
				if (legacyFlat.isFile()) {
					request.setAttribute("vendorLogoImgSrc",
							ctx + "/images/seller/logo/" + urlEncodePathSegment(logoFileName));
				}
			}
		}
	}

	private static String urlEncodePathSegment(String name) {
		return URLEncoder.encode(name, StandardCharsets.UTF_8).replace("+", "%20");
	}

	private String handleLogoUpload(HttpServletRequest request, int vendorNo) throws Exception {
		File saveRoot = resolveLogoStorageRoot(request.getServletContext());
		if (!saveRoot.exists() && !saveRoot.mkdirs()) {
			return "redirect:/seller/settings?logo=fail";
		}
		File vendorDir = new File(saveRoot, String.valueOf(vendorNo));
		if (!vendorDir.exists() && !vendorDir.mkdirs()) {
			return "redirect:/seller/settings?logo=fail";
		}

		try {
			for (Part part : request.getParts()) {
				if (!"logoFile".equals(part.getName()) || part.getSize() <= 0) {
					continue;
				}

				String submittedFileName = part.getSubmittedFileName();
				if (submittedFileName == null || submittedFileName.isBlank()) {
					return "redirect:/seller/settings?logo=invalid";
				}
				int slash = Math.max(submittedFileName.lastIndexOf('/'), submittedFileName.lastIndexOf('\\'));
				if (slash >= 0) {
					submittedFileName = submittedFileName.substring(slash + 1);
				}
				submittedFileName = submittedFileName.trim();
				if (submittedFileName.isEmpty()) {
					return "redirect:/seller/settings?logo=invalid";
				}

				String lower = submittedFileName.toLowerCase(Locale.ROOT);
				if (!(lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")
						|| lower.endsWith(".gif") || lower.endsWith(".webp"))) {
					return "redirect:/seller/settings?logo=invalid";
				}

				String previous = vendorDAO.getLogoImgByVendorNo(vendorNo);
				if (previous != null) {
					previous = previous.trim();
				}

				File dest = new File(vendorDir, submittedFileName);
				part.write(dest.getAbsolutePath());

				if (!dest.isFile() || dest.length() == 0L) {
					if (dest.isFile()) {
						dest.delete();
					}
					return "redirect:/seller/settings?logo=fail";
				}

				if (!vendorDAO.updateLogoImg(vendorNo, submittedFileName)) {
					dest.delete();
					return "redirect:/seller/settings?logo=fail";
				}

				deletePreviousLogoFiles(saveRoot.getAbsolutePath(), vendorNo, previous, submittedFileName);

				return "redirect:/seller/settings?logo=ok";
			}
			return "redirect:/seller/settings?logo=empty";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/seller/settings?logo=fail";
		}
	}

	private static void deletePreviousLogoFiles(String saveRoot, int vendorNo, String previous, String newName) {
		if (previous == null || previous.isEmpty()) {
			return;
		}
		if (previous.equals(newName)) {
			return;
		}
		File inVendor = new File(new File(saveRoot, String.valueOf(vendorNo)), previous);
		if (inVendor.isFile()) {
			inVendor.delete();
		}
		File flat = new File(saveRoot, previous);
		if (flat.isFile()) {
			flat.delete();
		}
	}
}
