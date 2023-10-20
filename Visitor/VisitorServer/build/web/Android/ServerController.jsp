<%@page import="java.util.TimeZone"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="connection.DbConnection"%>
<%
    DbConnection con = new DbConnection();

    String key = request.getParameter("key").trim();
    System.out.println("Key : " + key);
    //login
    if (key.equals("signin")) {

        String UNAME = request.getParameter("username");
        String info = "";
        String qry = "select * from `login` where username='" + request.getParameter("username") + "'and password='" + request.getParameter("password") + "' AND status='approved'";
        System.out.println("qry=" + qry);
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            info = v.get(4) + "&" + v.get(1);
            System.out.print(info);
            out.print(info);
        } else {
            System.out.println("else id=" + info);
            out.print("failed");
        }
    }

    //user_reg
    if (key.equals("user_reg")) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String check = "select * from `login` where username='" + request.getParameter("email") + "'";
        Iterator it = con.GetData(check).iterator();
        if (it.hasNext()) {
            out.print("already");
        } else {

            String qry = "INSERT INTO `user_registration`(`name`,`address`,`phone`,`email`) VALUES ('" + name + "','" + address + "','" + phone + "','" + email + "') ";
            String qry1 = "insert into login (uid,username,password,type)values((select max(uid)from user_registration),'" + email + "','" + password + "','user')";

            if (con.PutData(qry) > 0 && con.PutData(qry1) > 0) {
                out.print("success");
            } else {
                out.print("failed");
            }
        }
    }

    //shop_reg
    if (key.equals(
            "shop_reg")) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String location = request.getParameter("location");
        String password = request.getParameter("password");
        String qry = "INSERT INTO `shop_registration`(`name`,`address`,`location`,`phone`,`email`) VALUES ('" + name + "','" + address + "','" + location + "','" + phone + "','" + email + "') ";
        String qry1 = "insert into login (uid,username,password,type,`status`)values((select max(sid)from shop_registration),'" + email + "','" + password + "','shop','pending')";

        String check = "select * from `login` where username='" + request.getParameter("email") + "'";
        Iterator it1 = con.GetData(check).iterator();
        if (it1.hasNext()) {
            out.print("already");
        } else {

            if (con.PutData(qry) > 0 && con.PutData(qry1) > 0) {
                String qrygetId = "select max(sid)from shop_registration";
                Iterator it = con.GetData(qrygetId).iterator();
                String SID = "";
                if (it.hasNext()) {
                    Vector v = (Vector) it.next();
                    SID = v.get(0).toString().trim();
                }
                out.print(SID);
            } else {
                out.print("failed");
            }
        }
    }

    //add_notice
    if (key.equals(
            "add_notice")) {
        String subject = request.getParameter("subject");
        String details = request.getParameter("details");
        LocalDate date = LocalDate.now();
        String DATE = date.toString();
        String qry = "INSERT INTO `notification`(`subject`,`details`,`date`) VALUES('" + subject + "','" + details + "','" + DATE + "') ";
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

    //add_feedback
    if (key.equals(
            "add_feedback")) {
        String uid = request.getParameter("uid");
        String subject = request.getParameter("subject");
        String details = request.getParameter("details");
        LocalDate date = LocalDate.now();
        String DATE = date.toString();
        String qry = "INSERT INTO `feedback`(`uid`,`subject`,`details`,date) VALUES('" + uid + "','" + subject + "','" + details + "','" + date + "') ";
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

    //ShopRequestList
    if (key.equals(
            "ShopRequestList")) {

        String qry = "SELECT s.`sid`,s.`name`,s.`address`,s.`location`,s.`phone`,s.`email` FROM `shop_registration` s,`login` l WHERE s.`sid`=l.`uid` AND l.`type`='shop' AND l.`status`='pending' ";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("sid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("address", v.get(2).toString().trim());
                obj.put("location", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("email", v.get(5).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    //AdminRequsetAction
    if (key.equals(
            "AdminRequsetAction")) {
        String sid = request.getParameter("sid");
        String action = request.getParameter("action");
        String qry = "";
        if (action.trim().equals("approve")) {
            qry = "UPDATE `login` SET `status`='approved' WHERE `uid`='" + sid + "' AND `type`='shop'";

        } else {
            qry = "DELETE `login`  WHERE `uid`='" + sid + "' AND `type`='shop'";
            String qry1 = "DELETE `shop_registration` WHERE `sid`='" + sid + "'";
            con.PutData(qry1);
        }
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

    //AdminGetShopList
    if (key.equals(
            "AdminGetShopList")) {

        String qry = "SELECT s.`sid`,s.`name`,s.`address`,s.`location`,s.`phone`,s.`email` FROM `shop_registration` s,`login` l WHERE s.`sid`=l.`uid` AND l.`type`='shop' AND l.`status`='approved' ";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("sid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("address", v.get(2).toString().trim());
                obj.put("location", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("email", v.get(5).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    //getShopDataById
    if (key.equals(
            "getShopDataById")) {
        String id = request.getParameter("sid").trim();
        String info = "";
        String qry = "select * from shop_registration where sid= '" + id + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            JSONObject obj = new JSONObject();
            obj.put("sid", v.get(0).toString().trim());
            obj.put("name", v.get(1).toString().trim());
            obj.put("address", v.get(2).toString().trim());
            obj.put("location", v.get(3).toString().trim());
            obj.put("phone", v.get(4).toString().trim());
            obj.put("email", v.get(5).toString().trim());
            out.print(obj);
        } else {
            System.out.println("else id=" + info);
            out.print("failed");
        }
    }

    //markShopEntry
    if (key.equals(
            "markShopEntry")) {
        String uid = request.getParameter("uid");
        String sid = request.getParameter("sid");
        LocalDate date = LocalDate.now();
        String DATE = date.toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String TIME = dateFormat.format(calendar.getTime());
        String qry = "INSERT INTO `visitor_details`(`uid`,`sid`,`date`,`time`) VALUES('" + uid + "','" + sid + "','" + DATE + "','" + TIME + "') ";
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

//...................................... 
//UserGetVisitedList
    if (key.equals(
            "UserGetVisitedList")) {
        String uid = request.getParameter("uid");
        String qry = "SELECT s.`sid`,s.`name`,s.`address`,s.`location`,s.`phone`,s.`email`,v.`date`,v.`time` FROM `shop_registration` s,`visitor_details` v WHERE s.`sid`=v.`sid` AND  v.`uid`='" + uid + "' ";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("sid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("address", v.get(2).toString().trim());
                obj.put("location", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("email", v.get(5).toString().trim());
                obj.put("date", v.get(6).toString().trim());
                obj.put("time", v.get(7).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//getNotificationList
    if (key.equals(
            "getNotificationList")) {
        String uid = request.getParameter("uid");
        String qry = "SELECT `subject`,`details`,`date` FROM `notification`";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("subject", v.get(0).toString().trim());
                obj.put("details", v.get(1).toString().trim());
                obj.put("date", v.get(2).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//getProfileData
    if (key.equals(
            "getProfileData")) {

        String UID = request.getParameter("uid");
        String info = "";
        String qry = "select * from `user_registration` WHERE uid='" + UID + "'";
        System.out.println("qry=" + qry);
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            JSONObject obj = new JSONObject();
            obj.put("name", v.get(1).toString().trim());
            obj.put("address", v.get(2).toString().trim());
            obj.put("phone", v.get(3).toString().trim());
            obj.put("email", v.get(4).toString().trim());
//            System.out.print(""+obj);
            out.print(obj);
        } else {
            System.out.println("else id=" + info);
            out.print("failed");
        }
    }

//AddEmployee
    if (key.equals(
            "AddEmployee")) {
        String sid = request.getParameter("sid");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String qry = "INSERT INTO `employee`(sid,`name`,`address`,`phone`,`email`) VALUES ('" + sid + "','" + name + "','" + address + "','" + phone + "','" + email + "') ";
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

//GetEmployeesList
    if (key.equals(
            "getEmployeesList")) {
        String sid = request.getParameter("sid");
        String qry = "SELECT `eid`,`name`,`email`,`phone`,`address` FROM `employee`WHERE `sid`='" + sid + "'";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("eid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("email", v.get(2).toString().trim());
                obj.put("phone", v.get(3).toString().trim());
                obj.put("address", v.get(4).toString().trim());
                array.add(obj);
            }
            // System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//reportCovid
    if (key.equals(
            "reportCovid")) {
        String sid = request.getParameter("sid");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String details = request.getParameter("details");
        String vdate = request.getParameter("date");
        LocalDate date = LocalDate.now();
        String DATE = date.toString();

        String qry = "INSERT INTO `report_covid_alert`(`sid`,`name`,`address`,`details`,`date`,submited_date) VALUES ('" + sid + "','" + name + "','" + address + "','" + details + "','" + vdate + "','" + DATE + "') ";
        System.out.println(qry);
        if (con.PutData(qry) > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }

//UserGetAlertList
    if (key.equals(
            "UserGetAlertList")) {
        String uid = request.getParameter("uid");
        String qry = "SELECT s.`sid`,s.`name`,s.`address`,s.`location`,s.`phone`,s.`email`,v.`date`,v.`time`,r.`name`,r.`address`,r.`details`,r.`date`,r.`submited_date`   FROM `report_covid_alert` r,`visitor_details` v,`shop_registration` s WHERE r.`sid`=s.`sid` AND r.`sid`=v.`sid` AND v.`date`=r.`date` AND v.uid='" + uid + "' ";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("sid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("address", v.get(2).toString().trim());
                obj.put("location", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("email", v.get(5).toString().trim());
                obj.put("date", v.get(6).toString().trim());
                obj.put("time", v.get(7).toString().trim());
                obj.put("rp_name", v.get(8).toString().trim());
                obj.put("rp_address", v.get(9).toString().trim());
                obj.put("rp_details", v.get(10).toString().trim());
                obj.put("rp_date", v.get(11).toString().trim());
                obj.put("rp_postdate", v.get(12).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//AdminGetAlertList
    if (key.equals(
            "AdminGetAlertList")) {
        String qry = "SELECT s.`sid`,s.`name`,s.`address`,s.`location`,s.`phone`,s.`email`,v.`date`,v.`time`,r.`name`,r.`address`,r.`details`,r.`date`,r.`submited_date`   FROM `report_covid_alert` r,`visitor_details` v,`shop_registration` s WHERE r.`sid`=s.`sid` AND r.`sid`=v.`sid` AND v.`date`=r.`date`  ";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("sid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("address", v.get(2).toString().trim());
                obj.put("location", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("email", v.get(5).toString().trim());
                obj.put("date", v.get(6).toString().trim());
                obj.put("time", v.get(7).toString().trim());
                obj.put("rp_name", v.get(8).toString().trim());
                obj.put("rp_address", v.get(9).toString().trim());
                obj.put("rp_details", v.get(10).toString().trim());
                obj.put("rp_date", v.get(11).toString().trim());
                obj.put("rp_postdate", v.get(12).toString().trim());
                array.add(obj);
            }
            System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//getFeedbacks
    if (key.equals(
            "getFeedbacks")) {
        String qry = "SELECT u.`name`,u.`phone`,u.`email`,f.`subject`,f.`details`,f.`date` FROM `feedback` f,`user_registration` u WHERE f.`uid`=u.`uid`";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator it = con.GetData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("name", v.get(0).toString().trim());
                obj.put("email", v.get(1).toString().trim());
                obj.put("phone", v.get(2).toString().trim());
                obj.put("subject", v.get(3).toString().trim());
                obj.put("details", v.get(4).toString().trim());
                obj.put("date", v.get(5).toString().trim());

                array.add(obj);
            }
            // System.out.println("res : " + array);
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
%>