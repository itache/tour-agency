package me.itache.utils.tag;

import me.itache.dao.modifier.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.Writer;

/**
 * Renders page navigation panel.
 */
public class PaginationTag extends SimpleTagSupport {
    private final int PAGE_TO_DISPLAY = 10;
    private String uri;
    private String paginationClass = "pagination";
    private Pagination pagination;

    Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }

    @Override
    public void doTag() throws JspException {

        Writer out = getWriter();
        makeUri();
        boolean lastPage = pagination.getCurrentPageNumber() == pagination.getPageCount();
        int pgStart = Math.max(pagination.getCurrentPageNumber() - pagination.getItemsOnPageCount() / 2, 1);
        int pgEnd = pgStart + PAGE_TO_DISPLAY;
        if (pgEnd > pagination.getPageCount() + 1) {
            int diff = pgEnd - pagination.getPageCount();
            pgStart -= diff - 1;
            if (pgStart < 1)
                pgStart = 1;
            pgEnd = pagination.getPageCount() + 1;
        }

        try {
            out.write("<ul class=\"" + paginationClass + "\">");

            if (pagination.getCurrentPageNumber() > 1)
                out.write(constructLink(pagination.getCurrentPageNumber() - 1, "<i class=\"material-icons\">chevron_left</i>"));

            for (int i = pgStart; i < pgEnd; i++) {
                if (i == pagination.getCurrentPageNumber())
                    out.write(constructLink(pagination.getCurrentPageNumber(), String.valueOf(pagination.getCurrentPageNumber()), "active"));
                else
                    out.write(constructLink(i));
            }

            if (!lastPage)
                out.write(constructLink(pagination.getCurrentPageNumber() + 1, "<i class=\"material-icons\">chevron_right</i>"));

            out.write("</ul>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }

    private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }

    private String constructLink(int page, String text) {
        return constructLink(page, text, null);
    }

    private String constructLink(int page, String text, String className) {
        StringBuilder link = new StringBuilder("<li");

        link.append(" class=\"waves-effect ");
        if (className != null) {
            link.append(className);
        }
        link.append("\"");
        link.append(">")
                .append("<a href=\"")
                .append(uri.replace("##", String.valueOf(page)))
                .append("\">")
                .append(text)
                .append("</a></li>");
        return link.toString();
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void makeUri() {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");
        String url = (String) request.getAttribute("javax.servlet.forward.request_uri");
        if (queryString == null) {
            this.uri = url + "?page=##";
        } else {
            if (queryString.contains("page")) {
                queryString = queryString.replaceAll("page=[0-9]?", "page=##");
                this.uri = url + "?" + queryString;
            } else {
                this.uri = url + "?" + queryString + "&page=##";
            }
        }

    }


}
