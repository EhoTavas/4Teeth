/* eslint-disable linebreak-style */
/* eslint-disable quotes */
/* eslint-disable spaced-comment */
/* eslint-disable object-curly-spacing */
/* eslint-disable require-jsdoc */

import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

// inicializando o firebase admin
const firebase = admin.initializeApp();
const db = admin.firestore();

type Dentista = {
  nome: string,
  email: string,
  telefone: string,
  curriculo: string,
//  cep1: string,
//  endereco1: string,
//  cep2: string,
//  endereco2: string,
//  cep3: string,
//  endereco3: string,
  status: string,
  fcmToken: string | undefined,
//  foto: string,
  uid: string,
}

type Endereco = {
  cep: string,
  numero: string,
  complemento: string,
  rua: string,
  bairro: string,
  cidade: string,
  estado: string,
  dentista: string,
  ativo: string,
}

// type Emergency = {
//   name: string,
//   phone: string,
//   status: string,
// //  photos: String,
// }

/**
 * Tipo para facilitar o retorno
 * de qualquer função.
 * Basta usar esse objeto sempre como
 * retorno.
 */
type CustomResponse = {
  status: string | unknown,
  message: string | unknown,
  payload: unknown,
}

/**
   * Essa função pura (sem ser cloud function)
   * verifica se o parametro data contem:
   * nome, email, telefone e uid (lembrando que
   * a senha não armazenamos no perfil do firestore).
   * @param {any} data - objeto data (any).
   * @return {boolean} - true se tiver dados corretos
   * @param {MulticastMessage} multiMessage
   */
function hasAccountData(data: Dentista) {
  if (data.nome != undefined &&
    data.email != undefined &&
    data.telefone != undefined &&
    data.curriculo != undefined &&
  //    data.cep1 != undefined &&
  //    data.endereco1 != undefined &&
  //    data.cep2 != undefined &&
  //    data.endereco2 != undefined &&
  //    data.cep3 != undefined &&
  //    data.endereco3 != undefined &&
    data.status != undefined &&
    data.uid != undefined &&
    data.fcmToken != undefined) {
    return true;
  } else {
    return false;
  }
}
function hasAddressData(data: Endereco) {
  if (data.ativo != undefined &&
    data.bairro != undefined &&
    data.cep != undefined &&
    data.cidade != undefined &&
    data.complemento != undefined &&
    data.dentista != undefined &&
    data.estado != undefined &&
    data.numero != undefined &&
    data.rua != undefined) {
    return true;
  } else {
    return false;
  }
}

export const setUserProfile = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não fornecidos",
      payload: undefined,
    };
    // verificar se o objeto dentista foi fornecido
    const dentista = (data as Dentista);
    if (hasAccountData(dentista)) {
      try {
        const doc = await firebase.firestore()
          .collection("Dentista")
          .add(dentista);
        if (doc.id != undefined) {
          cResponse.status = "SUCCESS";
          cResponse.message = "Perfil de dentista inserido";
          cResponse.payload = JSON.stringify({docId: doc.id});
        } else {
          cResponse.status = "ERROR";
          cResponse.message = "Não foi possível inserir o perfil do dentista.";
          cResponse.payload = JSON.stringify({errorDetail: "doc.id"});
        }
      } catch (e) {
        let exMessage;
        if (e instanceof Error) {
          exMessage = e.message;
        }
        functions.logger.error("Erro ao incluir perfil:", dentista.email);
        functions.logger.error("Exception: ", exMessage);
        cResponse.status = "ERROR";
        cResponse.message = "Erro ao incluir usuário - Verificar Logs";
        cResponse.payload = null;
      }
    } else {
      cResponse.status = "ERROR";
      cResponse.message = "Perfil faltando informações";
      cResponse.payload = undefined;
    }
    return JSON.stringify(cResponse);
  });

export const updateUserProfile = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não fornecidos",
      payload: undefined,
    };
    const dentista = (data as Dentista);
    if (dentista && dentista.uid) {
      const dentistData = await firebase.firestore()
        .collection("Dentista")
        .where('uid', '==', data.uid)
        .get();

      if (!dentistData.empty) {
        const doc = dentistData.docs[0];
        try {
          await doc.ref.set(dentista, {merge: true});
          cResponse.status = "SUCCESS";
          cResponse.message = "Perfil de dentista atualizado";
          cResponse.payload = JSON.stringify({ docId: doc.id });
        } catch (e) {
          functions.logger.error("Erro ao atualizar perfil:", dentista.email);
          cResponse.status = "ERROR";
          cResponse.message = "Erro ao atualizar usuário - Verificar Logs";
          cResponse.payload = null;
        }
      } else {
        cResponse.status = "ERROR";
        cResponse.message = "Nenhum usuário encontrado com o uid fornecido";
        cResponse.payload = null;
      }
    }
    return JSON.stringify(cResponse);
  });

export const setUserAddresses = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não fornecidos",
      payload: undefined,
    };
    const endereco = (data as Endereco);
    if (hasAddressData(endereco)) {
      try {
        const doc = await firebase.firestore()
          .collection("Endereco")
          .add(endereco);
        if (doc.id != undefined) {
          cResponse.status = "STATUS";
          cResponse.message = "Endereço inserido";
          cResponse.payload = JSON.stringify({docId: doc.id});
        } else {
          cResponse.status = "ERROR";
          cResponse.message = "Não foi possível inserir o endereço";
          cResponse.payload = JSON.stringify({errorDetail: "doc.id"});
        }
      } catch (e) {
        let exMessage;
        if (e instanceof Error) {
          exMessage = e.message;
        }
        functions.logger.error("Erro ao incluir endereço: ", endereco.cep);
        functions.logger.error("Exception: ", exMessage);
        cResponse.status = "ERROR";
        cResponse.message = "Erro ao incluir endereço - Verificar Logs";
        cResponse.payload = null;
      }
    } else {
      cResponse.status = "ERROR";
      cResponse.message = "Endereço com informações insuficientes";
      cResponse.payload = undefined;
    }
    return JSON.stringify(cResponse);
  });

export const getUserProfile = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const uid = data.uid;
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não fornecidos",
      payload: undefined,
    };

    try {
      const querySnapshot = await firebase.firestore()
        .collection("Dentista")
        .where("uid", "==", uid)
        .get();

      if (!querySnapshot.empty) {
        const userData = querySnapshot.docs[0].data() as Dentista;
        cResponse.status = "SUCCESS";
        cResponse.message = "Perfil de usuário encontrado";
        cResponse.payload = JSON.stringify(userData);
      } else {
        cResponse.status = "ERROR";
        cResponse.message = "Perfil de usuário não encontrado";
        cResponse.payload = undefined;
      }
    } catch (e) {
      let exMessage;
      if (e instanceof Error) {
        exMessage = e.message;
      }
      functions.logger.error("Erro ao buscar perfil do usuário pelo UID:", uid);
      functions.logger.error("Exception: ", exMessage);
      cResponse.status = "ERROR";
      cResponse.message = "Erro ao buscar perfil do usuário - verificar Logs";
      cResponse.payload = null;
    }

    return JSON.stringify(cResponse);
  });

export const updateFcmToken = functions
  .region("southamerica-east1")
  .runWith({ enforceAppCheck: true })
  .https.onCall(async (data, context) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não informados",
      payload: undefined,
    };

    const uid = data.uid;
    const newToken = data.token;

    if (!uid || !newToken) {
      cResponse.status = "ERROR";
      cResponse.message = "UID ou Token não fornecido.";
      cResponse.payload = null;
      return JSON.stringify(cResponse);
    }

    try {
      const userSnapshot = await db
        .collection('Dentista')
        .where('uid', '==', uid)
        .get();

      if (userSnapshot.empty) {
        cResponse.status = "ERROR";
        cResponse.message = "Usuário não encontrado.";
        cResponse.payload = null;
        return JSON.stringify(cResponse);
      }

      const userRef = userSnapshot.docs[0].ref;

      await userRef.update({ fcmToken: newToken });

      cResponse.status = "SUCCESS";
      cResponse.message = "Token atualizado com sucesso.";
      cResponse.payload = JSON.stringify({ fcmToken: newToken });
    } catch (error) {
      let exMessage;
      if (error instanceof Error) {
        exMessage = error.message;
      }
      functions.logger.error("Erro ao atualizar token", exMessage);
      cResponse.status = "ERROR";
      cResponse.message = "Erro ao atualizar o FCM token - Verificar Logs";
      cResponse.payload = null;
    }

    return JSON.stringify(cResponse);
  });

export const sendFcmMessage = functions
  .region("southamerica-east1")
  .runWith({enforceAppCheck: false})
  .https
  .onCall(async (data, context) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não fornecidos ou incompletos",
      payload: undefined,
    };
    // enviar uma mensagem para o token que veio.
    if (data.fcmToken != undefined && data.textContent != undefined) {
      try {
        const message = {
          data: {
            text: data.textContent,
          },
          token: data.fcmToken,
        };
        const messageId = await firebase.messaging().send(message);
        cResponse.status = "SUCCESS";
        cResponse.message = "Mensagem enviada";
        cResponse.payload = JSON.stringify({messageId: messageId});
      } catch (e) {
        let exMessage;
        if (e instanceof Error) {
          exMessage = e.message;
        }
        functions.logger.error("Erro ao enviar mensagem");
        functions.logger.error("Exception: ", exMessage);
        cResponse.status = "ERROR";
        cResponse.message = "Erro ao enviar mensagem - Verificar Logs";
        cResponse.payload = null;
      }
    }
    return JSON.stringify(cResponse);
  });

export const answerEmergency = functions
  .region("southamerica-east1")
  .runWith({ enforceAppCheck: false })
  .https.onCall(async (data) => {
    const cResponse: CustomResponse = {
      status: "ERROR",
      message: "Dados não informados",
      payload: undefined,
    };

    const dentistData = await db
      .collection('Dentista')
      .where('uid', '==', data.dentist)
      .get();

    const answer = {
      time: admin.firestore.Timestamp.now().toDate().toISOString(),
      emergency: data.emergency,
      dentist: data.dentist,
      status: data.status,
      name: dentistData.docs[0].data().nome,
      phone: dentistData.docs[0].data().telefone,
    };

    try {
      const doc = await db.collection('Atendimentos').add(answer);
      if (doc.id != undefined) {
        cResponse.status = "SUCCESS";
        cResponse.message = "Resposta enviada";
        cResponse.payload = JSON.stringify({ docId: doc.id });
      } else {
        cResponse.status = "ERROR";
        cResponse.message = "Ocorreu um erro ao enviar a resposta";
        cResponse.payload = undefined;
      }
    } catch (e) {
      let exMessage;
      if (e instanceof Error) {
        exMessage = e.message;
      }
      functions.logger.error("Erro ao enviar resposta");
      functions.logger.error("Exception: ", exMessage);
      cResponse.status = "ERROR";
      cResponse.message = "Erro ao enviar reposta - Verificar Logs";
      cResponse.payload = null;
    }

    return JSON.stringify(cResponse);
  });

export const onEmergencyCreate = functions
  .region("southamerica-east1")
  .firestore
  .document("Emergencias/{EmergenciaID}")
  .onCreate(async (snapshot, context) => {
    try {
      const dentistaSnapshot = await db
        .collection('Dentista')
        .where('status', '==', '1')
        .get();
      const tokens = dentistaSnapshot.docs.map((doc) => doc.data().fcmToken);
      const multiMessage = {
        data: {
          text: "Nova emergência disponível",
        },
        tokens: tokens,
      };

      await firebase
        .messaging().sendEachForMulticast(multiMessage);
    } catch (error) {
      functions.logger.error("Erro critico notificacoes", error);
    }
  });
