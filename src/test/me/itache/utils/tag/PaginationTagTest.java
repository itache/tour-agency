package me.itache.utils.tag;

import org.junit.Test;
import me.itache.dao.modifier.Pagination;

import javax.servlet.jsp.JspException;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 */
public class PaginationTagTest extends PaginationTag {
    Writer writer = new StringWriter();
    @Test
    public void shouldProduceValidPaginationElement() throws JspException {
        String expected = "<ul class=\"pagination\">" +
                "<li class=\"active\">1</li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 2\">2</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 3\">3</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 4\">4</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 5\">5</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 6\">6</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 7\">7</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 8\">8</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 9\">9</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 10\">10</a></li>" +
                "<li class=\"waves-effect \"><a href=\"/tours?page = 2\"><i class=\"material-icons\">chevron_right</i></a></li>" +
                "</ul>";
        PaginationTagTest paginationTag = new PaginationTagTest();
        Pagination pagination = new Pagination();
        pagination.setItemsCount(200);
        pagination.setItemsOnPageCount(10);
        pagination.setCurrentPageNumber(1);
        paginationTag.setPagination(pagination);
        //paginationTag.makeUri("/tours?page = ##");
        //paginationTag.doTag();
        //Assert.assertEquals(expected, paginationTag.getWriter().toString());
    }

    @Override
    Writer getWriter() {
        return writer;
    }
}
