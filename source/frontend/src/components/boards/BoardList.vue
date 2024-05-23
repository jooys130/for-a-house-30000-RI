<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/notice.js";
import { storeToRefs } from "pinia";
import {useMemberStore} from "@/stores/member"
// import BoardListItem from "@/components/boards/item/BoardListItem.vue";

const router = useRouter();
const memberStore = useMemberStore(); 
const { userInfo } =storeToRefs(memberStore); 

// const selectOption = ref([
//   { text: "검색조건", value: "" },
//   { text: "글번호", value: "article_no" },
//   { text: "제목", value: "subject" },
//   { text: "작성자아이디", value: "user_id" },
// ]);

const articles = ref([]);
const headers = ref(
  [   
      { title: '글번호', value: 'noticeNo' },
      { title: '글쓴이', value: 'userId' },
      { title: '제목', value: 'subject' },
      { title: '내용', value: 'content' },
      { title: '조회수', value: 'hit' },
      { title: '작성일시', value: 'createdTime' },
    ],
)
  
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

// 마운트 되면 게시판 리스트 가져옴
onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data.articles);
      console.log(data.currentPage + "!!");
      console.log(data.totalPageCount + "!!!");

      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

// const changeKey = (val) => {
//   param.value.key = val;
// };



// const onPageChange = (val) => {
//   currentPage.value = val;
//   param.value.pgno = val;
//   getArticleList();
// };

const moveWrite = () => {
  router.push({ name: "article-write" });
};

const moveDetail = (event, { item }) => {
  console.log(item.noticeNo)
  router.push({ name: "article-view",  params: { noticeNo: item.noticeNo} })
}
</script>

<template>
  <div class="container">
    <div class="">
      <div style="font: 40px gMedium;" class="py-4 mb-4 text-center">공지사항</div>
      <div style="width : 100%; padding: 0 20%;">
        <!-- <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start"></div>
          <div class="col-md-5 offset-5">
            글검색
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div> -->
        <!-- <table id="grid-table">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in articles"
              :key="article.noticeNo"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table> -->
        <div class="my-4" v-if="userInfo===null"></div>
        <div v-else-if="userInfo.type === 0">
        <!-- <div> -->
          <button type="button" class="w_round_bar" @click="moveWrite">
                글쓰기
          </button>
        </div>
        <div class="my-4" v-else></div>
        <div>
          <v-data-table :headers="headers" :items="articles"  @click:row="moveDetail" hover></v-data-table>
        </div>
      </div>
      <!-- <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></VPageNavigation>  -->
      <!-- <v-pagination :current-page="currentPage" :length="totalPage" @pageChange="onPageChange"></v-pagination> -->
    </div>
  </div>
</template>

<style scoped>
table {
    width: 70%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #444444;
    /* border-left: 1px solid #444444; */
    padding: 10px;
  }
  th:first-child, td:first-child {
    border-left: none;
  }

    th, td {
      padding: 12px;
     
      border-bottom: 1px solid #ddd;
    }

    /* 테이블 헤더 스타일링 */
    th {
      background-color: #f2f2f2;
    }

    /* 번갈아가며 배경색 적용 */
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
</style>
