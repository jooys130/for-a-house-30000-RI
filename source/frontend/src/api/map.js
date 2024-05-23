import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getApt(param, success, fail) {
  console.log("got param " + param.sidoName + param.gugunName + param.dongName);
  local.get(`/apt/keySearch`, { params: param }).then(success).catch(fail);
}

function getAptDeal(aptCode, success, fail) {
  local.get(`/apt/dealSearch/${aptCode}`).then(success).catch(fail);
}

function getAptDealByName(aptName, success, fail) {
  local.get(`/apt/searchByName/${aptName}`).then(success).catch(fail);
}

function getAptNameByCode(param, success, fail) {
  local.get(`/apt/searchByCode?aptCodes=${param}`).then(success).catch(fail);
}

export { getApt, getAptDeal, getAptDealByName, getAptNameByCode };
