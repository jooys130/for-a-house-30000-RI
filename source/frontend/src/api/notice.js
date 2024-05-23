import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listArticle(param, success, fail) {
  local.get(`/notice`, { params: param }).then(success).catch(fail);
}

function detailArticle(noticeno, success, fail) {
  local.get(`/notice/${noticeno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("noticejs article", article);
  local.post(`/notice`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(noticeno, success, fail) {
  local.get(`/notice/modify/${noticeno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`/notice`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(noticeno, success, fail) {
  local.delete(`/notice/${noticeno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};
