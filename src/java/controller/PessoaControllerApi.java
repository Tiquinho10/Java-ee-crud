package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Dao.DataSource;
import Dao.PessoaDao;
import Model.Pessoa;

/**
 * Servlet implementation class MeuServlet
 */
@WebServlet("/api")
public class PessoaControllerApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PessoaDao pDao;
	private final DataSource dataSource = new DataSource();
	
	//objecto que transforma string num objecto
	private ObjectMapper mapper = new ObjectMapper();
       
   
    public PessoaControllerApi() {
        super();
        pDao = new PessoaDao(dataSource);
      
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		
		listPerson(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		insertPerson(req, res);
	}

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        updatePerson(req, resp);
           }
    
    @Override
      protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         removePerson(req, resp);
           }
    
    
        
        
	
	private void insertPerson(HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		
		
	    Pessoa pessoa = mapper.readValue(req.getInputStream(), Pessoa.class);
	    
	    pDao.inserir(pessoa);
	
	    //http stastus 201 - created
	    res.setStatus(HttpServletResponse.SC_CREATED);
		//enviar de volta o obj criado com o id gerado
	    
	   res.getWriter().write(mapper.writeValueAsString(pessoa));
	} 
        
        private void updatePerson(HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException{
             Pessoa pessoa = mapper.readValue(req.getInputStream(), Pessoa.class);
	    
	    pDao.atualizar(pessoa);
	
	    //http stastus 200 - ok
	    res.setStatus(HttpServletResponse.SC_OK);
		//enviar de volta o obj criado com o id gerado
	    
	   res.getWriter().write(mapper.writeValueAsString(pessoa));
            
        }
	
	private void listPerson(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		List<Pessoa> listPerson = pDao.readAll();
		
		res.setStatus(HttpServletResponse.SC_OK);
		//tranformar para json
		res.getWriter().write(mapper.writeValueAsString(listPerson));
		
	}
        
        
        private void removePerson(HttpServletRequest req, HttpServletResponse res) throws IOException{
            
            Pessoa pessoa = mapper.readValue(req.getInputStream(), Pessoa.class);
            
            pDao.remover(pessoa);
            
            res.setStatus(HttpServletResponse.SC_OK);
		//tranformar para json
		res.getWriter().write(mapper.writeValueAsString(pessoa));         
        }

}
 