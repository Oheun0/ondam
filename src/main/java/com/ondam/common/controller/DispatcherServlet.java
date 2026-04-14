package com.ondam.common.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.ondam.common.ProjectWebappPaths;

import com.ondam.ai.controller.AiIntroController;
import com.ondam.ai.controller.AiRecommendController;
import com.ondam.ai.controller.AiSearchController;
import com.ondam.cart.controller.CartController;
import com.ondam.coupon.controller.CouponController;
import com.ondam.gift.controller.GiftController;
import com.ondam.group.controller.FamilyGroupController;
import com.ondam.inquiry.controller.InquiryController;
import com.ondam.notification.controller.NotificationController;
import com.ondam.notification.controller.NotificationSettingController;
import com.ondam.orders.controller.OrderDetailController;
import com.ondam.orders.controller.OrderListController;
import com.ondam.orders.controller.OrderPaymentController;
import com.ondam.poke.controller.PokeController;
import com.ondam.product.controller.CategoryController;
import com.ondam.product.controller.ProductController;
import com.ondam.product.controller.SearchController;
import com.ondam.review.controller.ReviewController;
import com.ondam.review.controller.ReviewImageController;
import com.ondam.seller.controller.SellerAuthController;
import com.ondam.seller.controller.SellerDashboardController;
import com.ondam.seller.controller.SellerFindIdController;
import com.ondam.seller.controller.SellerNotificationController;
import com.ondam.seller.controller.SellerOrderController;
import com.ondam.seller.controller.SellerProductController;
import com.ondam.seller.controller.SellerResetPwController;
import com.ondam.seller.controller.SellerResetPwFormController;
import com.ondam.seller.controller.SellerResetSendCodeController;
import com.ondam.seller.controller.SellerSettingsController;
import com.ondam.seller.controller.SellerSettlementController;
import com.ondam.seller.controller.SellerSettlementDownloadController;
import com.ondam.seller.controller.SellerSignupController;
import com.ondam.shipment.controller.SellerShipmentController;
import com.ondam.shorts.controller.ShortsController;
import com.ondam.user.controller.AddressDeleteController;
import com.ondam.user.controller.AddressFormController;
import com.ondam.user.controller.AddressSaveController;
import com.ondam.user.controller.FindIdController;
import com.ondam.user.controller.FindPwdController;
import com.ondam.user.controller.KakaoCallbackController;
import com.ondam.user.controller.KakaoLoginController;
import com.ondam.user.controller.LoginController;
import com.ondam.user.controller.LogoutController;
import com.ondam.user.controller.MyPageController;
import com.ondam.user.controller.PreferenceSaveController;
import com.ondam.user.controller.ProfileAddressController;
import com.ondam.user.controller.ProfileController;
import com.ondam.user.controller.ProfilePreferenceController;
import com.ondam.user.controller.ProfileUpdateController;
import com.ondam.user.controller.ResetPwdController;
import com.ondam.user.controller.SignupCompleteController;
import com.ondam.user.controller.SignupStartController;
import com.ondam.user.controller.SignupStep0BasicController;
import com.ondam.user.controller.SignupStep1BasicController;
import com.ondam.user.controller.SignupStep2AddressController;
import com.ondam.user.controller.SignupStep3PreferenceController;
import com.ondam.user.controller.UserCouponController;
import com.ondam.user.controller.UserIdCheckController;
import com.ondam.wallet.controller.WalletController;
import com.ondam.wish.controller.WishController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/") // 모든 요청 받기
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1,  // 1MB (이 이상이면 임시 디렉토리에 저장)
	    maxFileSize = 1024 * 1024 * 20,       //파일 한 장당 최대 20MB
	    maxRequestSize = 1024 * 1024 * 100    //전체 요청 최대 100MB
		) //사진 받아오기
public class DispatcherServlet extends HttpServlet {
    
    // URL과 실행할 컨트롤러를 매핑해서 저장하는 저장소
    private Map<String, Controller> handlerMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // 매핑 정보 등록
        handlerMapping.put("/login", new LoginController());
        handlerMapping.put("/notification", new NotificationController());
        handlerMapping.put("/main", new MainController());
        handlerMapping.put("/category", new CategoryController());
        handlerMapping.put("/shorts", new ShortsController());
        handlerMapping.put("/group", new FamilyGroupController());
        handlerMapping.put("/wallet", new WalletController());
        handlerMapping.put("/preview", new PreviewController());
        handlerMapping.put("/signup-start", new SignupStartController());
        handlerMapping.put("/signup-step0-basic", new SignupStep0BasicController());
        handlerMapping.put("/signup-step1-basic", new SignupStep1BasicController());
        handlerMapping.put("/signup-step2-address", new SignupStep2AddressController());
        handlerMapping.put("/signup-step3-preference", new SignupStep3PreferenceController());
        handlerMapping.put("/signup-complete", new SignupCompleteController());
        handlerMapping.put("/kakao-login", new KakaoLoginController());
        handlerMapping.put("/kakao-callback", new KakaoCallbackController());
        handlerMapping.put("/check-userid", new UserIdCheckController());
        handlerMapping.put("/find-id", new FindIdController());
        handlerMapping.put("/find-pwd", new FindPwdController());
        handlerMapping.put("/reset-pwd", new ResetPwdController());
        handlerMapping.put("/coupon", new CouponController());
        handlerMapping.put("/gift", new GiftController());
        handlerMapping.put("/mypage", new MyPageController());
        handlerMapping.put("/logout", new LogoutController());
        handlerMapping.put("/profile", new ProfileController());
        handlerMapping.put("/profile/update", new ProfileUpdateController());
        handlerMapping.put("/profile-address", new ProfileAddressController());
        handlerMapping.put("/preference", new ProfilePreferenceController());
        handlerMapping.put("/address/save", new AddressSaveController());
        handlerMapping.put("/address/form", new AddressFormController());
        handlerMapping.put("/address/delete", new AddressDeleteController());
        handlerMapping.put("/profile/preference", new PreferenceSaveController());
        handlerMapping.put("/product", new ProductController());
        handlerMapping.put("/poke", new PokeController());
        handlerMapping.put("/review", new ReviewController());
        handlerMapping.put("/reviewImage", new ReviewImageController());
        handlerMapping.put("/cart", new CartController());
        handlerMapping.put("/wish", new WishController());
        handlerMapping.put("/ai-recommend", new AiRecommendController());
        handlerMapping.put("/notification/notification-setting", new NotificationSettingController());
        handlerMapping.put("/order/order-list", new OrderListController());
        handlerMapping.put("/order/order-detail", new OrderDetailController());
        handlerMapping.put("/payment", new OrderPaymentController());
        handlerMapping.put("/ai-intro", new AiIntroController());
        handlerMapping.put("/inquiry/inquiry-list", new InquiryController());
        handlerMapping.put("/inquiry", new InquiryController());
        handlerMapping.put("/search", new SearchController());
        handlerMapping.put("/aiSearch", new AiSearchController());
        handlerMapping.put("/seller/auth", new SellerAuthController());
        handlerMapping.put("/seller/dashboard", new SellerDashboardController());
        SellerSettingsController sellerSettingsController = new SellerSettingsController();
        handlerMapping.put("/seller/settings", sellerSettingsController);
        handlerMapping.put("/seller/settings/save", sellerSettingsController);
        handlerMapping.put("/seller/settings/logo", sellerSettingsController);
        handlerMapping.put("/seller/auth/signup", new SellerSignupController());
        handlerMapping.put("/seller/auth/find-id", new SellerFindIdController());
        handlerMapping.put("/seller/auth/reset-password", new SellerResetPwController());
        handlerMapping.put("/seller/auth/reset-password/send-code", new SellerResetSendCodeController());
        handlerMapping.put("/seller/auth/reset-password-form", new SellerResetPwFormController());
        handlerMapping.put("/seller/shipment", new SellerShipmentController());
        SellerProductController sellerProductController = new SellerProductController();
        handlerMapping.put("/seller/product", sellerProductController);
        handlerMapping.put("/seller/product/form", sellerProductController);
        handlerMapping.put("/seller/product/edit", sellerProductController);
        handlerMapping.put("/seller/product/save", sellerProductController);
        handlerMapping.put("/seller/product/update", sellerProductController);
        handlerMapping.put("/seller/product/generate-easy-desc", sellerProductController);
        handlerMapping.put("/userCoupon", new UserCouponController());
        handlerMapping.put("/seller/shorts/list", new com.ondam.seller.controller.SellerShortsListController());
        handlerMapping.put("/seller/shorts/form", new com.ondam.seller.controller.SellerShortsFormController());
        handlerMapping.put("/seller/shorts/api", new com.ondam.shorts.controller.ShortsGeneratorController());
        handlerMapping.put("/seller/dashboard", new SellerDashboardController());
        handlerMapping.put("/seller/order", new SellerOrderController());
        handlerMapping.put("/seller/settlement/list", new SellerSettlementController());
        handlerMapping.put("/seller/settlement/download", new SellerSettlementDownloadController());
        handlerMapping.put("/seller/notification", new SellerNotificationController());
    }
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // URL 분석 (ContextPath 제외한 순수 경로)
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length()); 

        /* 업로드는 프로젝트 src/main/webapp 에 저장되는데, GET 은 배포 디렉터리에서만 찾는 경우가 있어
         * /images/*, /uploads/* 는 먼저 프로젝트 웹앱 경로에서 파일을 찾아 서빙 */
        if (path.startsWith("/images/") || path.startsWith("/uploads/")) {
            if (serveFileFromProjectWebappIfPresent(request.getServletContext(), response, path)) {
                return;
            }
        }

        // 확장자가 있거나(파일), 특정 정적 폴더 경로인 경우 톰캣 기본 서블릿에 위임
        if (path.contains(".") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            request.getServletContext().getNamedDispatcher("default").forward(request, response);
            return;
        }

        // 담당 컨트롤러 꺼내기
        Controller controller = handlerMapping.get(path);
        
        if (controller == null) {
            response.sendError(404, "해당 경로를 찾을 수 없습니다.");
            return;
        }

        try {
            // 논리 컨트롤러 실행 (비즈니스 로직 처리)
            String viewName = controller.execute(request, response);
            
            if (response.isCommitted()) return;
            
            // 뷰 리졸버 역할 (경로 조립 및 포워딩)
            if (viewName != null) {
                if (viewName.startsWith("redirect:")) {
                    // 리다이렉트 처리 (예: "redirect:/main") '/' 가 오기 전이 9글자
                    String redirectPath = contextPath + viewName.substring(9);
                    response.sendRedirect(redirectPath);
                } else {
                    // 포워딩 처리 (WEB-INF 경로 자동 조립)
                    String viewPath = "/WEB-INF/views/" + viewName + ".jsp";
                    request.getRequestDispatcher(viewPath).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "서버 내부 오류 발생");
        }
    }

    /**
     * 로컬에서 업로드된 파일이 배포본이 아닌 프로젝트 webapp 에만 있을 때 응답.
     * @return true 이면 이미 응답을 썼음
     */
    private boolean serveFileFromProjectWebappIfPresent(ServletContext servletContext,
            HttpServletResponse response, String path) throws IOException {
        File f = ProjectWebappPaths.resolveExistingFileUnderWebapp(servletContext, path);
        if (f == null) {
            return false;
        }
        String mime = getServletContext().getMimeType(path);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        response.setContentType(mime);
        long len = f.length();
        if (len <= Integer.MAX_VALUE) {
            response.setContentLength((int) len);
        } else {
            response.setContentLengthLong(len);
        }
        try (InputStream in = new FileInputStream(f)) {
            in.transferTo(response.getOutputStream());
        }
        return true;
    }
}