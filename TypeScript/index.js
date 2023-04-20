import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import axios from 'axios';
    
const app = express();
const port = 3000;
const url = 'http://localhost:3000/usuarios';

app.use(bodyParser.json());
app.use(cors());

app.get('/', (req, res) => {
  res.send('API funcionando!');
});

app.listen(port, () => {
  console.log(`Servidor iniciado na porta ${port}`);
});

app.post('/usuarios', (req, res) => {
    const usuario = req.body;
    // salva o usuário no banco de dados ou em outro lugar
    res.send('Usuário salvo com sucesso!');
  });

const usuario = {
    nome: 'João',
    email: 'joao@email.com',
    senha: '12345'
};

axios.post(url, usuario)
  .then(function (response) {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });