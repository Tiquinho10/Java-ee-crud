/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Login;
import Model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class LoginDao {
    private DataSource dataSource;
    
    public LoginDao(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    public void inserir(Login c) {
        String url = "INSERT INTO login values(?, ? )";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());
          
            ps.executeUpdate();
            ps.close();

                System.out.print("Salvo com sucesso");
        } catch (SQLException e) {
            System.err.println("erro ao inserir " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

    }

     public boolean validate(Login c) {
        String url = "SELECT * FROM login where username = ? and password = ?";
        boolean status = false;
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
             ps.setString(1, c.getUsername());
             ps.setString(2, c.getPassword());
            
            
            
            ResultSet rs = ps.executeQuery();

           
            status = rs.next();
          
            ps.close();

            //return status;

        } catch (SQLException e) {
            System.err.println("erro ao listar " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

      return status;
    }
   
    
}
