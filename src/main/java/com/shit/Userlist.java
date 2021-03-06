package com.shit;



import com.shit.databasesource.DataConnection;
import com.shit.databasesource.dao.UserDao;
import com.shit.databasesource.model.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/userlist")
public class Userlist extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        HttpSession session = req.getSession();
        UserDao userDao = new UserDao(DataConnection.getConnection());
        User user = (User) session.getAttribute("user");

        if (1 == user.getRoleId()) {
            try {
                List<User> list = userDao.getAll();
                req.setAttribute("list", list);
                req.getRequestDispatcher("list.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            pw.print("Для просмотра данного контента, нужно зайти под учетной записью администратора!");
        }
    }
}
