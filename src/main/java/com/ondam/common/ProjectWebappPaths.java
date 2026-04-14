package com.ondam.common;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import jakarta.servlet.ServletContext;

/**
 * 업로드·정적 서빙이 같은 웹앱 루트를 보도록 경로를 맞춤.
 * <p>
 * 우선순위:
 * <ol>
 * <li>시스템 프로퍼티 {@code ondam.webapp.root}</li>
 * <li>{@code web.xml} 컨텍스트 파라미터 {@code ondam.projectWebappRoot} (워크스페이스의 {@code .../src/main/webapp} 절대경로 — 비어 있으면 무시)</li>
 * <li>{@link ServletContext#getRealPath(String)} {@code "/"} — Eclipse/Tomcat 배포 웹 루트 (업로드와 GET이 여기로 맞춰짐)</li>
 * <li>{@code user.dir}·클래스패스 등 기존 추론</li>
 * </ol>
 */
public final class ProjectWebappPaths {

	public static final String CTX_PARAM_PROJECT_WEBAPP_ROOT = "ondam.projectWebappRoot";

	private ProjectWebappPaths() {
	}

	/** 컨텍스트 없을 때(테스트 등)만 사용. 가능하면 {@link #getWebappRoot(ServletContext)}. */
	public static File getWebappRoot() {
		return resolveWebappRootWithoutServletContext();
	}

	public static File getWebappRoot(ServletContext ctx) {
		String override = System.getProperty("ondam.webapp.root");
		if (override != null) {
			String t = override.trim();
			if (!t.isEmpty()) {
				return new File(t);
			}
		}

		if (ctx != null) {
			String fromXml = ctx.getInitParameter(CTX_PARAM_PROJECT_WEBAPP_ROOT);
			if (fromXml != null) {
				String u = fromXml.trim();
				if (!u.isEmpty()) {
					File f = new File(u);
					if (f.isDirectory()) {
						return f.getAbsoluteFile();
					}
				}
			}
			try {
				String rp = ctx.getRealPath("/");
				if (rp != null) {
					File f = new File(rp);
					if (f.isDirectory()) {
						return f.getAbsoluteFile();
					}
				}
			} catch (Exception ignored) {
			}
		}

		return resolveWebappRootWithoutServletContext();
	}

	private static File resolveWebappRootWithoutServletContext() {
		String override = System.getProperty("ondam.webapp.root");
		if (override != null) {
			String t = override.trim();
			if (!t.isEmpty()) {
				return new File(t);
			}
		}

		File fromUserDir = resolveFromUserDir();
		if (fromUserDir != null) {
			return fromUserDir;
		}

		File fromClasspath = resolveFromClasspath();
		if (fromClasspath != null) {
			return fromClasspath;
		}

		return new File(new File(System.getProperty("user.dir", ".")).getAbsoluteFile(), "src/main/webapp");
	}

	private static File resolveFromUserDir() {
		File cwd = new File(System.getProperty("user.dir", ".")).getAbsoluteFile();
		for (int i = 0; i < 6; i++) {
			File w = new File(cwd, "src/main/webapp");
			if (w.isDirectory()) {
				return w;
			}
			if (cwd.getParentFile() == null) {
				break;
			}
			cwd = cwd.getParentFile();
		}
		return null;
	}

	private static File resolveFromClasspath() {
		try {
			URL url = ProjectWebappPaths.class.getProtectionDomain().getCodeSource().getLocation();
			if (url == null) {
				return null;
			}
			File start = new File(url.toURI());
			File dir = start.isFile() ? start.getParentFile() : start;
			File cur = dir;
			for (int i = 0; i < 14 && cur != null; i++) {
				File w = new File(cur, "src/main/webapp");
				if (w.isDirectory()) {
					return w;
				}
				cur = cur.getParentFile();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static File profileImagesDirectory(ServletContext ctx) {
		return new File(getWebappRoot(ctx), "images" + File.separator + "profile");
	}

	public static File sellerLogoDirectory(ServletContext ctx) {
		return new File(getWebappRoot(ctx), "images" + File.separator + "seller" + File.separator + "logo");
	}

	public static File uploadsProductsDirectory(ServletContext ctx) {
		return new File(getWebappRoot(ctx), "uploads" + File.separator + "products");
	}

	/**
	 * 컨텍스트 기준 경로(예: {@code /images/profile/a.jpg})에 해당하는 파일이
	 * 웹앱 루트 아래에 있으면 반환. 경로 탈출({@code ..})은 차단.
	 */
	public static File resolveExistingFileUnderWebapp(ServletContext ctx, String webPath) {
		if (webPath == null || !webPath.startsWith("/")) {
			return null;
		}
		String rel = webPath.substring(1);
		if (rel.isEmpty() || rel.contains("..")) {
			return null;
		}
		try {
			File root = getWebappRoot(ctx);
			Path base = root.toPath().toAbsolutePath().normalize();
			Path resolved = base.resolve(rel).normalize();
			if (!resolved.startsWith(base)) {
				return null;
			}
			File f = resolved.toFile();
			return f.isFile() ? f : null;
		} catch (Exception e) {
			return null;
		}
	}
}
