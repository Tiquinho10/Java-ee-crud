package Dao;

import Model.Pessoa;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PessoaDao {

    private DataSource dataSource;

    public PessoaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void inserir(Pessoa c) {
        String url = "INSERT INTO pessoa(nome, telefone, endereco, sexo) values( ?, ?, ? ,?)";
        String generatedColumns[] = {"id"};
        try {
        
        	PreparedStatement ps = dataSource.getConnection().prepareStatement(url, generatedColumns);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getSexo());
          
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                c.setId(id);
            }
            ps.close();

                System.out.print("Salvo com sucesso");
        } catch (SQLException e) {
            System.err.println("erro ao inserir " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }
       
    
    }

    public void atualizar(Pessoa c) {
        String url = "UPDATE pessoa SET nome = ?, telefone = ? , endereco = ?, sexo = ? WHERE id = ?";
       
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getSexo());
            ps.setInt(5 ,c.getId());
            
            System.out.println("Id da pessoa " + c.getId());
            
            System.out.println("Pessoa : " + c.toString());
          

            ps.executeUpdate();
            ps.close();

            System.out.print("Atualizado com sucesso");
        } catch (SQLException e) {
            System.err.println("erro ao atualizar " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

    }

  
  
    public void remover(Pessoa c) {
        String url = "DELETE FROM pessoa  WHERE id = ?";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
            ps.setInt(1, c.getId());

            ps.executeUpdate();
            ps.close();

            System.out.println( "removido com sucesso");
        } catch (SQLException e) {
            System.err.println("erro ao remover " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

    }

    public ArrayList<Pessoa> readAll() {
        String url = "SELECT * FROM pessoa";
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
            ResultSet rs = ps.executeQuery();

            ArrayList<Pessoa> lista = new ArrayList<Pessoa>();

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setSexo(rs.getString("sexo"));

                lista.add(pessoa);

            }

            ps.close();

            return lista;

        } catch (SQLException e) {
            System.err.println("erro ao listar " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

        return null;
    }
    
    public Pessoa selectByid(int id) {
        String url = "SELECT * FROM pessoa where id = ?";
        Pessoa pessoa = null;
        try {
            PreparedStatement ps = dataSource.getConnection().prepareStatement(url);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

           

            while (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setSexo(rs.getString("sexo"));


            }

            ps.close();

            return pessoa;

        } catch (SQLException e) {
            System.err.println("erro ao listar " + e.getMessage());
        } catch (Exception e) {
            System.err.println("erro geral" + e.getMessage());
        }

        return null;
    }
    


}
