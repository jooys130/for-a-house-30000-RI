<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getAptDealByName } from "@/api/map.js";
import { storeToRefs } from "pinia";
import {useMemberStore} from "@/stores/member"

const router = useRouter();
const memberStore = useMemberStore(); 
const { userInfo } =storeToRefs(memberStore); 

const articles = ref([]);
const headers = ref(
  [   
      { title: '시', value: 'sidoName' },
      { title: '구', value: 'gugunName' },
      { title: '동', value: 'dong' },
      { title: '아파트 이름', value: 'apartmentName' },
      { title: '평균 거래 가격', value: 'dealAmount' },
    ],
)
  
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  word: "",
});

const getAptList = (apartmentName) => {
  console.log(apartmentName);
  getAptDealByName(apartmentName,
    ({ data }) => {
      console.log(data);
      articles.value = data;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
      console.log(articles.value);
    },
    (error) => {
      console.error(error);
    }
);
};

// const moveDetail = (event, { item }) => {
//   console.log(item.noticeNo)
//   router.push({ name: "article-view",  params: { noticeNo: item.noticeNo} })
// }
</script>

<template>
  <div class="container">
    <div class="">
      <div style="font: 80px gMedium;" class="py-4 mb-4 text-center"></div>
      <div style="width : 100%; padding: 0 20%;">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start"></div>
          <div class="col-md-5 offset-5">
            <!-- 아파트 이름 검색 -->
            <form class="d-flex">
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class=" form-control b_round_bar size_mid"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="size_sm w_round_bar custom_h2" style="z-index: 10;" type="button" @click="getAptList(param.word)">검색</button>
              </div>
            </form>
          </div>
        </div>
      <template v-if="articles.length === 0">                                                               
            <div class="note">
              <p>아파트 이름을 입력해주세요</p>
            </div>
      </template>
      <template v-else-if="articles.length !== 0">
        <div>
          <v-data-table
            :headers="headers"
            :items="articles"
            hover
            class="custom-table">
          </v-data-table>
        </div>
      </template>
      </div>
    </div>
  </div>
</template>

<script>
// ... (기존 코드)
</script>

<style scoped>
html {
  background:none;
}
.custom-table {
  border-collapse: collapse;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.custom-table th,
.custom-table td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.custom-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.custom-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.custom-table tr:hover {
  background-color: #f5f5f5;
}

.form-control {
  background-color: #f9f9f9;
  border-color: #e0e0e0;
}

.note{
  display: flex;
  justify-content: center;
  color: white;
  margin: 6% 25%;
  font: 30px gBold;
}
</style>
