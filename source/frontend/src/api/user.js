import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  await local.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function regist(param, success, fail) {
  await local.post(`/user/regist`, param).then(success).catch(fail);
}

async function deleteUser(userId, success, fail) {
  await local.delete(`/user/delete/${userId}`).then(success).catch(fail);
}

async function modify(param, success, fail) {
  await local.post(`/user/update`, param).then(success).catch(fail);
}

async function likesearchAll(userId, success, fail) {
  await local.get(`/user/mylike/${userId}`).then(success).catch(fail);
}

// 우선 여기 둘게요..
async function chatGpt(param, success, fail) {
  await local.post(`/chat-gpt/question`, param).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  regist,
  deleteUser,
  modify,
  likesearchAll,
  chatGpt
};
