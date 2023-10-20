/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author user
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;


/**
 *
 * @author user
 */
public class DbConnection {
          
         Connection con=null;
         Statement st=null;
         ResultSet rs=null;
         ResultSetMetaData rsm=null;
         Vector rowdata=null;
         
    
    
    public DbConnection(){
         
        try {
            if(con==null){
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor","root","");
            st=con.createStatement();
            System.out.println("sucess connection");
                } 
           }
        catch (Exception ex) {
             System.out.println("access problam.."+ex);
        }
      
    }
    
    public int PutData(String qry){
        
       try{
           
          
           return st.executeUpdate(qry);
       }
       catch(Exception e){
           System.out.println("Access failed.."+e);
       }
       return 0;
    }
    
    
    public Vector GetData(String qry){        
        try {
            System.out.println("In getdata string");
            rowdata= new Vector();
            rs=st.executeQuery(qry);
            rsm=rs.getMetaData();
            int colcount=rsm.getColumnCount();
            while(rs.next()){
            Vector coldata=new Vector();
            for(int i=0;i<colcount;i++){
                    coldata.add(rs.getString((i+1)));
                }
                rowdata.add(coldata);
                
                    }
            return rowdata;
        
        } catch (Exception e) {
            System.out.println("access error..");
        }
        return rowdata;
        
        
    }

}

