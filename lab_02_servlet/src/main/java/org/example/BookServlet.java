package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private List<String[]> books = Arrays.asList(
            new String[]{"Преступление и наказание", "Достоевский", "1866"},
            new String[]{"Мастер и Маргарита", "Булгаков", "1966"},
            new String[]{"1984", "Оруэлл", "1949"}
    );
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        int count = books.size();
        StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        for (String[] book : books) {
            String bookName = book[0];
            String bookAuthor = book[1];
            String bookYear = book[2];
            html.append("<div class='book'>")
                    .append("<h3>").append(bookName).append("</h3>")
                    .append("<p>Автор: ").append(bookAuthor).append("</p>")
                    .append("<p>Год: ").append(bookAuthor).append("</p>")
                    .append("</div>");

        }
        html.append("</html>\n");
        request.setAttribute("booksCount", count);
        request.setAttribute("booksList", html.toString());
        request.setAttribute("bookColor", "#f0f8ff"); // цвет фона

        request.getRequestDispatcher("/books.html").forward(request, response);

    }
}
