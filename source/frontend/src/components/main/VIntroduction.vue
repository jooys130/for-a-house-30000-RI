<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const goMapSearchView = () => {
  router.push({ name: "mapsrch" });
};
const goKeywordSearchView = () => {
  router.push({ name: "keywordsrch" });
};
</script>

<template>
  <div id="intro">
    <h1 class="custom_h1">집찾아삼만리</h1>
    <h2 class="custom_h2">아파트 매매 정보 검색 서비스</h2>
    <div>
      <button class="w_round_bar size_big" @click="goKeywordSearchView">
        이름 기반 검색
      </button>
      <button class="w_round_bar size_big" @click="goMapSearchView">
        위치 기반 검색
      </button>
    </div>
  </div>
</template>

<style scoped>
h1,
h2 {
  color: aliceblue;
}
h1 {
  font-size: 70px;
  text-shadow: 2px 4px rgba(0, 0, 0, 0.2);
}
h2 {
  font-size: 30px;
  text-shadow: 2px 2px rgba(0, 0, 0, 0.2);
}
button {
  font-family: gMedium;
}
#intro {
  float: left;
  text-align: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  height:92vh;
  padding-bottom: 10vh;

  /* 범위 확인용 */
  /* border: 5px rgba(255, 255, 255, 0.5) solid; */
}
</style>
