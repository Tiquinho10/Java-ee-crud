
 //ajax

 let pessoas = [];
 let tbl = document.querySelector('.table-content');
 let txt = document.querySelector('.txt');
 let caption = document.querySelector('.caption');
 const btn_add = document.querySelector('#btn_add');
 
 //window.addEventListener('load', function(){
     
     fetch('/newWeb/api')
           .then(function(response){
 
             response.json().then(function(data){
 
                 pessoas = data;

                 

                 console.log(pessoas)
                 
                 tblSize();

                 pessoas.forEach((ssid, password) =>{
                      nome = ssid,
                      password = password;
                 })
                 
               // console.log(data.luiz)
                 let homens = pessoas.filter(p => p.sexo == 'M')
                 let mulheres = pessoas.filter(p => p.sexo == 'F')
                 
                
               
                
                 addTabela();       
             })            
           })  
 //})
 

 //--------------modal----
 
 const modalBook = () =>{
   const btn_submit = document.querySelector('#submit');
   caption.innerText = 'Adicionar Pessoa';
    //let roomType = document.querySelector('#roomType');
    let modalBg = document.querySelector('.modal-bg');
    let close = document.querySelector('.close-btn');
    
     // roomType.value = y;
    //modalBtn.addEventListener('click', function(){
       modalBg.classList.add('bg-active');
    //})
    
    close.addEventListener('click', function(){
              
         clear(btn_submit, modalBg, 'bg-active');
       //modalBg.classList.remove('bg-active');
       return false;
    })
    
    
    btn_submit.addEventListener('click', function(){
     
      const nome = document.querySelector('#nome').value;
      const telefone = document.querySelector('#telefone').value;
      const endereco = document.querySelector('#endereco').value;
      const sexo = document.querySelector('#sexo').value;
  
      //json
       let pessoa = {
           nome : nome,
           telefone : telefone,
           endereco : endereco,
           sexo : sexo
       }

       
        fetch('/newWeb/api', {
           method : 'POST',
            body : JSON.stringify(pessoa)
         }).then(function(response){
                console.log(response.status)
             if(response.status === 201){
                //sucesso
               response.json().then(function(data){
                   console.log(data)
 
                   pessoas.push(data)
                  
                   tblSize();
                  addTabela();
                  clear(btn_submit, modalBg, 'bg-active');
                })
                 
                }else{
                   console.log("erro ")
               }
            
        })
      
     
         
      modalBg.classList.remove('bg-active');
      clear(btn_submit, modalBg, 'bg-active');
      return;
   });
    

    } 
       
    btn_add.addEventListener('click', modalBook)
    
    const modalBookPut = (id, nome, telefone, endereco, sexo) =>{
   
      const btn_ed = document.querySelector('#submit');
      caption.innerText = 'Editar Pessoa';
    
      const idIn = document.querySelector('#id');
        const nomeIn = document.querySelector('#nome');
        const telefoneIn = document.querySelector('#telefone');
        const enderecoIn = document.querySelector('#endereco');
        const sexoIn = document.querySelector("#sexo");

        idIn.value = id;
        nomeIn.value = nome;
        telefoneIn.value = telefone;
        enderecoIn.value = endereco;
        sexoIn.value = sexo;

      
      let modalBg = document.querySelector('.modal-bg');
      let close = document.querySelector('.close-btn');
      
      
      //modalBtn.addEventListener('click', function(){
         modalBg.classList.add('bg-active');
      //})
      //event.stopPropagation();
     
      close.addEventListener('click', function(){
         //remove o evento on click
         clear(btn_ed, modalBg, 'bg-active');
         return;
      })

     
   
      btn_ed.addEventListener('click', function(){
      
         let pessoa = {
            id : id,
            nome : nomeIn.value,
            telefone : telefoneIn.value,
            endereco : enderecoIn.value,
            sexo : sexoIn.value
         }

      
         fetch('/newWeb/api',{ 
               method : 'PUT',
               body : JSON.stringify(pessoa)
         }).then(function(response){
                  console.log(response)
               if(response.status === 200){
                  
                   clear(btn_ed, modalBg, 'bg-active');
                 response.json().then(function(data){


                  let pIndex = pessoas.findIndex((pessoa) => {

                     return pessoa.id == data.id;
                  })
                  
                  pessoas[pIndex] = data;
               
                  addTabela();
                  
                })
               }else{
                  alert('erro ao atualizar');
               }

         })
        
        //modalBg.classList.remove('bg-active');
        return;
         })
           
      
      }
      
    
         function confirmar(id){
             
        let resposta = confirm("Confirma a exclusao");

         let pessoa = {
            id : id
         }
        
         if(resposta === true){
             fetch('/newWeb/api', {
               method : 'delete',
               body: JSON.stringify(pessoa)
             }
             ).then(function(response){

                  if(response.status === 200){

                     response.json().then(function(data){
                        pIndex = pessoas.findIndex((pessoa) =>{
                           return pessoa.id == data.id;
                        })

                       let listaAtual = pessoas.filter((pessoa) =>{

                        return pessoa.id !== data.id;
                       })

                       pessoas = listaAtual;

                       tblSize();
                       addTabela();

                     })


                  }else{
                     alert('Erro ao deletar');
                  }
               

             })

         }
    }

   let addTabela = function(){
      let tbody = document.querySelector('.pessoa-table');
         tbody.innerText = '';
      pessoas.forEach(function(pessoa){
          console.log(pessoa.nome)
         //adicinar uma linha na tabel
         
         //
         
         let tr = tbody.insertRow();
         
         let id = tr.insertCell();
         let nome = tr.insertCell();
         let telefone = tr.insertCell();
         let endereco = tr.insertCell();
         let sexo = tr.insertCell();
         let acoes = tr.insertCell();
         
         id.innerText = pessoa.id;
         nome.innerText = pessoa.nome;
         telefone.innerText = pessoa.telefone;
         endereco.innerText = pessoa.endereco;
         sexo.innerText = pessoa.sexo;

         let btn_editar = document.createElement('button');
         let btn_excluir = document.createElement('button');
         
        let txt_editar = document.createTextNode("Editar");
        let txt_excluir = document.createTextNode("Excluir");

        btn_editar.classList.add('btn_editar');
        btn_excluir.classList.add('btn_excluir');

        btn_editar.addEventListener("click", function () {
             modalBookPut(pessoa.id, pessoa.nome, pessoa.telefone, pessoa.endereco, pessoa.sexo);
          });


        btn_excluir.addEventListener("click", () =>{
         confirmar(pessoa.id);
        });


       
         

        btn_editar.appendChild(txt_editar);
        btn_excluir.appendChild(txt_excluir);

        acoes.appendChild(btn_editar);
        acoes.appendChild(btn_excluir);


     })
   }

   function tblSize(){
      if(pessoas.length == 0){
         tbl.classList.add('hide');
         txt.innerText = 'Ainda sem pessoas Registradas';
         }else{
           txt.innerText = '';
           tbl.classList.remove('hide');
           }
   }

   function clear(listener, modal, classe){
      const idIn = document.querySelector('#id');
      const nomeIn = document.querySelector('#nome');
      const telefoneIn = document.querySelector('#telefone');
      const enderecoIn = document.querySelector('#endereco');
      const sexoIn = document.querySelector("#sexo");

      idIn.value = '';
      nomeIn.value = ''
      telefoneIn.value = ''
      enderecoIn.value = '';
      sexoIn.value = 'Selecione o sexo';

      listener.replaceWith(listener.cloneNode(true));
      modal.classList.remove(classe);
   }

 