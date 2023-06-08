/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcon;

import java.sql.ResultSet;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
//file upload pkgs//
import java.io.File;

import java.io.FileOutputStream;
import org.apache.commons.codec.binary.Base64;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;


//file upload pkgs//
/** 
 *
 * @author shweta
 */

@WebService(serviceName="placement")
public class placement {
    
      DbConnect db;
    ResultSet rs;
    
    public placement() {
        db = new DbConnect();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public String login(
    @WebParam(name="username") String username,
    @WebParam(name="password") String password
    ) {
        //TODO write your implementation code here:
        String ret="na";
        try {
            String qry="select * from login where username='"+username+"' and  password='"+password+"' ";
            rs=db.selectDetails(qry);
            if(rs.next()){
            ret=rs.getString("login_id")+"#"+rs.getString("usertype");
            }
            else{
            ret="na";
            }

            
        } catch (Exception e) {
        }
        return ret;
    }
    
    
    
//    @WebMethod(operationName = "Student_register")
//    public String Student_register(
//    @WebParam(name="firstname") String firstname,
//    @WebParam(name="lastname") String lastname,
//    @WebParam(name="email") String email,
//    @WebParam(name="phone") String phone,
//    @WebParam(name="regno") String regno,
//    @WebParam(name="password") String password
//    ) {
//        //TODO write your implementation code here:
//        String ret="failed";
//        try {
//            
//            String qry="insert into login values(null,'"+regno+"','"+password+"','student')";
//            System.out.println(qry);
//
//            int a =db.insertDetails(qry);
//            if(a>0)
//            {
//            String qry1="insert into user values(null,(select max(login_id ) from login),'"+firstname+"','"+lastname+"','"+email+"','"+phone+"')";
//            System.out.println(qry1);
//            int b =db.insertDetails(qry1);
//            
//           if(b>0)
//           {
//               ret="success";
//           }
//            }
//            
//          
//
//            
//        } catch (Exception e)
//        {
//        }
//            
//        return ret;
//    }
     @WebMethod(operationName = "company_register")
    public String company_register(
            @WebParam(name="companyname")String companyname,
            @WebParam(name="place")String place,
            @WebParam(name="email")String email,
            @WebParam(name="phone")String phone,
            @WebParam(name="licenseno")String licenseno,
            @WebParam(name="username")String username,
            @WebParam(name="password")String password,
            @WebParam(name="file")String file
  ){
          String ret="failed";
try {
            SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            String filename = sd.format(new java.util.Date());
            filename = filename + ".jpg";
            String filenamess = filename;
            FilePath fp = new FilePath();
            String folder = fp.getPath();
            File fil = new File(folder + filename);
            if(!fil.exists())
            {
                fil.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(fil);
            byte[] img=Base64.decodeBase64(file);
            fos.write(img); 
            fos.flush();
            fos.close();
                    
            String qry1 = "insert into login values(null,'"+username+"', '"+password+"', 'company')";
            db.insertDetails(qry1);
            String qry2 = "insert into user values(null,(select max(login_id) from login),'"+companyname+ "','"+place+"','"+phone+"','"+email+"','"+licenseno+"','"+filenamess+"')";
            db.insertDetails(qry2);
            
                ret = "ok";
            
        } catch (Exception e) {
            ret = e.toString();
        }
        return ret;
    }
 
    
    
 
    @WebMethod(operationName = "Student_register")
    public String Student_register(
    @WebParam(name="firstname") String firstname,
    @WebParam(name="lastname") String lastname,
    @WebParam(name="email") String email,
    @WebParam(name="phone") String phone,
    @WebParam(name="regno") String regno,
    @WebParam(name="password") String password,
     @WebParam(name="file")String file
  ){
        String ret="failed";
try {
            SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            String filename = sd.format(new java.util.Date());
            filename = filename + ".jpg";
            String filenamess = filename;
            FilePath fp = new FilePath();
            String folder = fp.getPath();
            File fil = new File(folder + filename);
            if(!fil.exists())
            {
                fil.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(fil);
            byte[] img=Base64.decodeBase64(file);
            fos.write(img);
            fos.flush();
            fos.close();
                    
                  System.out.println("------------------------------------"+fil);

            
            String qry="insert into login values(null,'"+regno+"','"+password+"','student')";
            System.out.println(qry);

            int a =db.insertDetails(qry);
            if(a>0)
            {
            String qry1="insert into user values(null,(select max(login_id ) from login),'"+firstname+"','"+lastname+"','"+phone+"','"+email+"','"+filenamess+"','pendingSHplacement')";
             System.out.println(qry1);
            int b =db.insertDetails(qry1);
            
           if(b>0)
           {
               ret="success";
           }
            
         
            
        }
}catch (Exception e) {
            ret = e.toString();
        }
        return ret;

    }
    
  
    @WebMethod(operationName = "viewuser")
    public String viewuser() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from user group by image";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("fname") + "#" + rs.getString("lname") + "#"+rs.getString("phone")+ "#"+rs.getString("email")+"#"+rs.getString("login_id")+"#"+rs.getString("image")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
       @WebMethod(operationName = "view_enquiry")
    public String view_enquiry() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from enquiry inner join user using(user_id)";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret+ rs.getString("enquiry_id") + "#" + rs.getString("fname") + "#" + rs.getString("enquiry") + "#"+rs.getString("reply")+ "#"+rs.getString("date")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
//    
//     @WebMethod(operationName = "Manage_post")
//    public String Manage_post(
//           
//    @WebParam(name="Post") String post,
//    @WebParam(name="Qualification") String qualification,  
//    @WebParam(name="Postfor") String postfor,
//    @WebParam(name="Details") String detail,
//    @WebParam(name="Date") String date,
//     @WebParam(name="file")String file
//  ){
//       String ret="failed";
//try {
//            SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
//            String filename = sd.format(new java.util.Date());
//            filename = filename + ".jpg";
//            String filenamess = filename;
//            FilePath fp = new FilePath();
//            String folder = fp.getPath();
//            File fil = new File(folder + filename);
//            if(!fil.exists())
//            {
//                fil.createNewFile();
//            }
//            FileOutputStream fos=new FileOutputStream(fil);
//            byte[] img=Base64.decodeBase64(file);
//            fos.write(img);
//            fos.flush();
//            fos.close();
//                    
//                  System.out.println("------------------------------------"+fil);
//
//            
////while (rs.next()) {
////    // code to be executed for each row
//////    int id = rs.getInt("id");
//////    String name = rs.getString("name");
////    int phone = rs.getInt("phone");
////    
////}
//
//            
//           String qry="insert into post values(null,'"+post+"','"+qualification+"','"+postfor+"','"+detail+"',curdate(),'"+date+"','"+filename+"')";
//            System.out.println(qry);
//
//            int a =db.insertDetails(qry);
//            if(a>0)
//          
//           {
//               String qry1="select * from user";
// 
//                rs = db.selectDetails(qry1);
//               if (rs.next()) {
//                        ret = "";
//                        do {                    
//                            ret = ret+rs.getString("phone")+"$";
//                        } while (rs.next());
////                        ret="success";
//            }
////               ret="success";
//               
//               
//           }
//            
//         
//            
//        
//}catch (Exception e) {
//            ret = e.toString();
//        }
//        return ret;
//
//        
//  }
//    
@WebMethod(operationName = "Manage_post")
public String Manage_post(
    @WebParam(name="Post") String post,
    @WebParam(name="Qualification") String qualification,
    @WebParam(name="Postfor") String postfor,
    @WebParam(name="Details") String detail,
    @WebParam(name="Date") String date,
    @WebParam(name="file")String file
) {
    String ret = "failed";
    try {
        // Saving the post to database
        SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String filename = sd.format(new java.util.Date());
        filename = filename + ".jpg";
        String filenamess = filename;
        FilePath fp = new FilePath();
        String folder = fp.getPath();
        File fil = new File(folder + filename);
        if (!fil.exists()) {
            fil.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(fil);
        byte[] img = Base64.decodeBase64(file);
        fos.write(img);
        fos.flush();
        fos.close();
        String qry = "insert into post values(null,'" + post + "','" + qualification + "','" + postfor + "','" + detail + "',curdate(),'" + date + "','" + filename + "')";
        System.out.println(qry);
        int a = db.insertDetails(qry);
        if (a > 0) {
            // Retrieving email addresses from database
            String qry1 = "select * from user";
            rs = db.selectDetails(qry1);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret+rs.getString("phone")+"$";
                } while (rs.next());
            }
            // Sending email to retrieved email addresses
            String[] recipients = ret.split("\\$");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("aparnasnair1997m@gmail.com", "aparnasworld");
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aparnabhaasi@gmail.com"));
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject("New Post Added");
            message.setText("A new post has been added to the Placement cell");
            Transport.send(message);
        }
    } catch (Exception e) {
        ret = e.toString();
    }
    return ret;
}


    @WebMethod(operationName = "Manage_exam")
    public String Manage_exam(
    @WebParam(name="Post_id") String pid,
   
    @WebParam(name="Exam") String exam,
    @WebParam(name="Date") String date
   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
        try {
            
            String qry="insert into exam values(null,'"+pid+"','"+exam+"','"+date+"',curdate(),'pending')";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
    
    @WebMethod(operationName = "view_post")
    public String view_post() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from post";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret+rs.getString("post_id")+"#"+rs.getString("post") + "#" + rs.getString("qualification") + "#"+rs.getString("postfor")+ "#"+rs.getString("details")+ "#"+rs.getString("date")+ "#"+rs.getString("image")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
        
     @WebMethod(operationName = "chatdetail")
    public String chatdetail(
     @WebParam(name="receiver_id")String receiver_id ,
     @WebParam(name="sender_id")String sender_id 
    ) {
        String ret="failed";
        try 
        {
            String qry="SELECT * from chat where (sender_id='"+sender_id+"' and receiver_id='"+receiver_id+"') or (sender_id='"+receiver_id+"' and receiver_id='"+sender_id+"') ";
           System.out.println(qry);

            rs=db.selectDetails(qry);
            if(rs.next())
            {
             ret="";
                do {    

                    ret=ret+rs.getString("sender_id")+"#"+rs.getString("receiver_id")+"#"+rs.getString("message")+"#"+rs.getString("date")+"$";
//                               System.out.println(rs.getString("date"));

                } while (rs.next());
            }
        } catch (Exception e) {
            ret="failed";
        }
        
        //TODO write your implementation code here:
        return ret;
    }
    
     @WebMethod(operationName = "chat")
    public String chat(
     @WebParam(name="sender_id")String sender_id ,
            @WebParam(name="receiver_id")String receiver_id ,
            @WebParam(name="details")String details 
    ) {
        String ret="failed";
        try 
        {
            
            String qry1="insert into chat values(null,'"+sender_id+"','"+receiver_id+"','"+details+"',curtime())";
            db.insertDetails(qry1);
            ret="success";
        }
         catch (Exception e) {
             ret="failed";
        }
        
        //TODO write your implementation code here:
        return ret;
    }    
     @WebMethod(operationName = "viewapplied")
    public String viewapplied(
             @WebParam(name="applied_id")String aid
    ) {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from apply inner join post using(post_id) where apply_id='"+aid+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("post") + "#" + rs.getString("details") + "#"+rs.getString("qualification")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
     @WebMethod(operationName = "Send_reply")
    public String Send_reply(
      @WebParam(name="Eid") String eid,      
    @WebParam(name="Reply") String reply   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
        try {
            
            String qry="update enquiry set reply='"+reply+"' where enquiry_id='"+eid+"'";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
    
     @WebMethod(operationName = "view_exam")
    public String view_exam(
    @WebParam(name="Post_id") String Post_id   
    
    )  {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from exam inner join post using(post_id) where post_id='"+Post_id+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret + rs.getString("post_id") + "#" + rs.getString("post") + "#"+rs.getString("exam")+ "#"+rs.getString("examdate")+ "#"+rs.getString("date")+ "#"+rs.getString("exam_id")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
      @WebMethod(operationName = "view_exam_attended")
    public String view_exam_attended(
    @WebParam(name="logid") String logid   
    
    )  {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from exam inner join question using(exam_id) inner join questionanswer using(question_id) inner join answers using(question_id) inner join user using(user_id) where user_id=(select user_id from user where login_id='"+logid+"')";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("login_id") + "#" + rs.getString("examdate") + "#"+rs.getString("date")+ "#"+rs.getString("fname")+ "#"+rs.getString("lname")+"$"+rs.getString("mark")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
    
     @WebMethod(operationName = "view_chatted_user")
    public String view_chatted_user() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from user";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("login_id") +"#"+ rs.getString("fname") + " " + rs.getString("lname")+"#"+ rs.getString("image")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
    @WebMethod(operationName = "student_view_post")
    public String student_view_post() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from post";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                   ret = ret+rs.getString("post_id")+"#"+rs.getString("post") + "#" + rs.getString("qualification") + "#"+rs.getString("postfor")+ "#"+rs.getString("details")+ "#"+rs.getString("date")+ "#"+rs.getString("image")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
    
    
     @WebMethod(operationName = "Apply")
    public String Apply(
    @WebParam(name="pid") String pid,
    @WebParam(name="logid") String logid,
    @WebParam(name="Post") String Post,
    @WebParam(name="Qualification") String Qualification
   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
        try {
            
            String qry="insert into apply values(null,'"+pid+"',(select user_id from user where login_id='"+logid+"'),'"+Post+"','"+Qualification+"')";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
    @WebMethod(operationName = "student_view_applied")
    public String student_view_applied(
    @WebParam(name="logid")String logid
    ) {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select *,`apply`.`qualification` AS aq from apply inner join post using(post_id) inner join exam using(post_id) where user_id=(select user_id from user where login_id='"+logid+"') group by details";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret+ rs.getString("exam_id") + "#" + rs.getString("post") + "#" + rs.getString("details") + "#"+rs.getString("aq")+ "#"+rs.getString("post_id")+"$"; 
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
    
    
     @WebMethod(operationName = "Send_enquiry")
    public String Send_enquiry(
    @WebParam(name="logid") String logid,
    @WebParam(name="Enquiry") String enq
   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
        try {
            
            String qry="insert into enquiry values(null,(select user_id from user where login_id='"+logid+"'),'"+enq+"','pending',curdate())";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
    @WebMethod(operationName = "Student_view_enquiry")
    public String Student_view_enquiry() {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from enquiry";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret+ rs.getString("enquiry_id") + "#" + rs.getString("enquiry") + "#"+rs.getString("reply")+ "#"+rs.getString("date")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
     @WebMethod(operationName = "Student_view_exam_attended")
    public String Student_view_exam_attended(
    @WebParam(name="logid") String logid   
    
    )  {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from exam inner join question using(exam_id) inner join questionanswer using(question_id) inner join answers using(question_id) inner join user using(user_id) where user_id=(select user_id from user where login_id='"+logid+"')";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("login_id") + "#" + rs.getString("examdate") + "#"+rs.getString("date")+ "#"+rs.getString("fname")+ "#"+rs.getString("lname")+"$"+rs.getString("mark")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
    
     @WebMethod(operationName = "Delete")
    public String Delete(
    @WebParam(name="post_id") String postid
   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
        try {
            
            String qry="delete from post where post_id='"+postid+"' ";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
        
    }
     @WebMethod(operationName = "Add_work_details")
    public String Add_work_details(
  
    @WebParam(name="logid") String logid,
    @WebParam(name="work") String wrk,
     @WebParam(name="file")String file
  ){
        String ret="failed";
try {
            SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            String filename = sd.format(new java.util.Date());
            filename = filename + ".jpg";
            String filenamess = filename;
            FilePath fp = new FilePath();
            String folder = fp.getPath();
            File fil = new File(folder + filename);
            if(!fil.exists())
            {
                fil.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(fil);
            byte[] img=Base64.decodeBase64(file);
            fos.write(img);
            fos.flush();
            fos.close();
                    
                  System.out.println("------------------------------------"+fil);

            
           
            {
            String qry1="insert into working_details values(null,(select user_id from user where login_id='"+logid+"'),'"+wrk+"','"+file+"')";
             System.out.println(qry1);
            int b =db.insertDetails(qry1);
            
           if(b>0)
           {
               ret="success";
           }
            
         
            
        }
}catch (Exception e) {
            ret = e.toString();
        }
        return ret;

    }
     @WebMethod(operationName = "Manage_questions")
    public String Manage_questions(
    @WebParam(name="exam_id") String eid,
    @WebParam(name="Question") String qns,
    @WebParam(name="ss") String ss,
    @WebParam(name="o1") String o1,
    @WebParam(name="o2") String o2,
    @WebParam(name="o3") String o3,
    @WebParam(name="o4") String o4,
    @WebParam(name="o5") String o5,
    @WebParam(name="outs") String outs
   
    
    ) {
        //TODO write your implementation code here:
        String ret="failed";
         try {
            
            String qry="insert into question values(null,'"+eid+"','"+qns+"')";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            if(Integer.parseInt(ss)==2)
            {
                if(Integer.parseInt(outs)==1)
                {
                 String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','Yes')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                }
                else if(Integer.parseInt(outs)==2){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','Yes')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                }
            }
            else if(Integer.parseInt(ss)==3)
            {
                if(Integer.parseInt(outs)==1)
                {
                 String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','Yes')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                }
                else if(Integer.parseInt(outs)==2){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','Yes')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                }
                else if(Integer.parseInt(outs)==3){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','Yes')";
                 db.insertDetails(qry3);
                }
                
                 
                 
                 
            }
            else if(Integer.parseInt(ss)==4)
            {
                   if(Integer.parseInt(outs)==1)
                {
                 String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','Yes')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                 String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                }
                else if(Integer.parseInt(outs)==2){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','Yes')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                 String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                }
                else if(Integer.parseInt(outs)==3){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','Yes')";
                 db.insertDetails(qry3);
                  String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                }
                else if(Integer.parseInt(outs)==4){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                  String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','Yes')";
                 db.insertDetails(qry4);
                }
                
                
                
            }
            else if(Integer.parseInt(ss)==5)
            {
                
                if(Integer.parseInt(outs)==1)
                {
                 String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','Yes')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                 String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                 String qry5="insert into questionanswer values(null,(select max(question_id) from question),'"+o5+"','No')";
                 db.insertDetails(qry5);
                }
                else if(Integer.parseInt(outs)==2){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','Yes')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                 String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                 String qry5="insert into questionanswer values(null,(select max(question_id) from question),'"+o5+"','No')";
                 db.insertDetails(qry5);
                }
                else if(Integer.parseInt(outs)==3){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','Yes')";
                 db.insertDetails(qry3);
                  String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                 String qry5="insert into questionanswer values(null,(select max(question_id) from question),'"+o5+"','No')";
                 db.insertDetails(qry5);
                }
                else if(Integer.parseInt(outs)==4){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                  String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','Yes')";
                 db.insertDetails(qry4);
                 String qry5="insert into questionanswer values(null,(select max(question_id) from question),'"+o5+"','No')";
                 db.insertDetails(qry5);
                }
                else if(Integer.parseInt(outs)==5){
                  String qry1="insert into questionanswer values(null,(select max(question_id) from question),'"+o1+"','No')";
                 int id=db.insertDetails(qry1);
                 System.out.println(qry1);
                 String qry2="insert into questionanswer values(null,(select max(question_id) from question),'"+o2+"','No')";
                 db.insertDetails(qry2);
                 System.out.println(qry2);
                 String qry3="insert into questionanswer values(null,(select max(question_id) from question),'"+o3+"','No')";
                 db.insertDetails(qry3);
                  String qry4="insert into questionanswer values(null,(select max(question_id) from question),'"+o4+"','No')";
                 db.insertDetails(qry4);
                 String qry5="insert into questionanswer values(null,(select max(question_id) from question),'"+o5+"','Yes')";
                 db.insertDetails(qry5);
                }
                
                 
                  
            }
            
            
           if(a>0)
           {
               ret="success";
           }
            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
     @WebMethod(operationName = "Student_view_questions")
    public String Student_view_questions(
    @WebParam(name="logid") String logid , 
            @WebParam(name="exam_id") String exam_id  
    
    )  {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="SELECT * FROM `question` INNER JOIN `questionanswer` USING(`question_id`) WHERE `question_id`  NOT IN(SELECT `question_id` FROM `answers` WHERE `user_id`=(SELECT `user_id` FROM `user` WHERE `login_id`='"+logid+"')) AND `exam_id`='"+exam_id+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("question_id") + "#" + rs.getString("question")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
//     @WebMethod(operationName = "Student_view_questions")
//    public String Student_view_questions() {
//        //TODO write your implementation code here:
//        String ret = "na";
//        
//        try {
//           
//            String qry="select * from question inner join exam using(exam_id)";
//        rs = db.selectDetails(qry);
//        System.out.println(qry);
//            if (rs.next()) {
//                ret = "";
//                do {                    
//                    ret = ret + rs.getString("question_id")+"#" + rs.getString("exam_id") + "#" + rs.getString("question")+"$";
//                } while (rs.next());
//            }
//        } catch (Exception e) {
//            ret = "na";
//        }
//        return ret;
//    }
     @WebMethod(operationName = "Student_view_options")
    public String Student_view_options(
                @WebParam(name="logid") String logid , 
            @WebParam(name="q_id") String qid,
            @WebParam(name="eid") String eid
    ) {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="SELECT * FROM `question` where question_id='"+qid+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) {
                ret = "";
                do {                    
                    ret = ret + rs.getString("question_id")+"#"  + rs.getString("question")+"^";
                } while (rs.next());
            }
             String qry1="SELECT * FROM `questionanswer` where question_id='"+qid+"'";
//            + " INNER JOIN `questionanswer` USING(`question_id`) WHERE `question_id`  NOT IN(SELECT `question_id` FROM `answers` WHERE `user_id`=(SELECT `user_id` FROM `user` WHERE `login_id`='"+logid+"')) AND `exam_id`='"+eid+"'";
        rs = db.selectDetails(qry1);
        System.out.println(qry1);
            if (rs.next()) {
               
                do {                    
                    ret = ret + rs.getString("qanswer_id")+"#" + rs.getString("option") + "#" + rs.getString("status")+"$";
                } while (rs.next());
            }
            
            
        } catch (Exception e) {
            ret = "na";
        }
        return ret;
    }
     @WebMethod(operationName = "view_exams")
    public String view_exams(
       
    
    )  {
        //TODO write your implementation code here:
        String ret = "na";
        
        try {
           
            String qry="select * from exam inner join post using(post_id)";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret+ rs.getString("exam_id") + "#"  +  rs.getString("post") + "#"+rs.getString("exam")+ "#"+rs.getString("examdate")+ "#"+rs.getString("date")+"$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
     @WebMethod(operationName = "Admin_update_post")
    public String Admin_update_post(
 @WebParam(name="post_id")String post_id 
       
    ) {
        String ret="failed";
        try 
        {
            String qry="select * from post where post_id='"+post_id+"' ";
           System.out.println(qry);

            rs=db.selectDetails(qry);
            if(rs.next())
            {
             ret="";
                do {     
                    ret=ret+rs.getString("post")+"#"+
                            rs.getString("qualification")+"#"+
                            rs.getString("postfor")+"#"+
                            rs.getString("date")+"#";

                    
                } while (rs.next());
            }
        } catch (Exception e) {
            ret="failed";
        }
        
        //TODO write your implementation code here:
        return ret;
    }
    
     @WebMethod(operationName = "Student_view_exams")
    public String Student_view_exams(
        @WebParam(name="Post_id")String post_id 
            
    
    )  {
        //TODO write your implementation code here:
        String ret = "";
        
        try {
           
            String qry="select * from exam inner join post using(post_id) where post_id='"+post_id+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret+ rs.getString("exam_id") + "#"  +  rs.getString("post") + "#"+rs.getString("exam")+ "#"+rs.getString("examdate")+ "#"+rs.getString("date")+ "#" +rs.getString("post_id")+ "$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
@WebMethod(operationName = "User_answer")
    public String User_answer(
           
    @WebParam(name="logid") String logid,
    @WebParam(name="qanswer_id") String qanswer_id,
    @WebParam(name="mark") String mark,
    @WebParam(name="question_id") String q_id
    
    ) {
        //TODO write your implementation code here:
        String ret="failed"; 
        try {
            
            String qry="insert into answers values(null,'"+q_id+"',(select user_id from user where login_id='"+logid+"'),'"+qanswer_id+"','"+mark+"')";
            System.out.println(qry);

            int a =db.insertDetails(qry);
           
            
           if(a>0)
           {
               ret="success";
           }
            
            
          

            
        } catch (Exception e)
        {
        }
            
        return ret;
    }
     @WebMethod(operationName = "Student_view_examss")
    public String Student_view_examss(
        @WebParam(name="Post_id")String post_id 
            
    
    )  {
        //TODO write your implementation code here:
        String ret = "";
        
        try {
           
            String qry="select * from exam inner join post using(post_id) where post_id='"+post_id+"'";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret+ rs.getString("exam_id") + "#"  +  rs.getString("post") + "#"+rs.getString("exam")+ "#"+rs.getString("examdate")+ "#"+rs.getString("date")+ "#" +rs.getString("post_id")+ "$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
     @WebMethod(operationName = "view_placed")
    public String view_placed(
       
            
    
    )  {
        //TODO write your implementation code here:
        String ret = "";
        
        try {
           
            String qry="select * from  working_details";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret+ rs.getString("fname")+"#"+rs.getString("lname")+ "#"  +  rs.getString("work")+ "$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
    @WebMethod(operationName = "view_marklist")
    public String view_marklist(
       
            
    
    )  {
        //TODO write your implementation code here:
        String ret = "";
        
        try {
           
            String qry="SELECT * FROM  answers INNER JOIN question USING(question_id) INNER JOIN exam USING(exam_id) INNER JOIN USER USING(user_id)";
        rs = db.selectDetails(qry);
        System.out.println(qry);
            if (rs.next()) 
            {
                System.out.println(rs);
                do {                    
                    ret = ret+ rs.getString("fname")+"#"+rs.getString("lname")+ "#"  +  rs.getString("work")+ "$";
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e);
            ret = "na";
        }
        return ret;
    }
}
    