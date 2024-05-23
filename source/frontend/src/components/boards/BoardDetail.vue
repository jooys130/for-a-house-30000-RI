<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/notice";
import { storeToRefs } from "pinia";
import {useMemberStore} from "@/stores/member"


const route = useRoute();
const router = useRouter();

const memberStore = useMemberStore(); 
const { userInfo } =storeToRefs(memberStore); 
// const articleno = ref(route.params.articleno);
const { noticeNo } = route.params;

const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  detailArticle(
    noticeNo,
    ({ data }) => {
      article.value = data;
      console.log(data);
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { noticeNo } });
}

function onDeleteArticle() {
  deleteArticle(
    noticeNo,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div style="font: 40px gMedium;" class="text-center py-4 mb-4">글보기</div>
      <v-container style="width: 60vw;">
        <v-row style=" font : 30px gMedium; height: 70px;">
          <v-text-field label="제목" variant="solo-filled" v-model="article.subject" readonly></v-text-field>
        </v-row>
        <v-row style="font : 20px gLight; height: 90px;">
          <v-col>
            <v-text-field label="글쓴이" variant="solo-filled" v-model="article.userId" readonly></v-text-field>
          </v-col>
          <v-col>
            <v-text-field label="생성 시간" variant="solo-filled" v-model="article.createdTime" readonly></v-text-field>
          </v-col>
        </v-row>
        <v-row style="height: 150px;">
          <v-textarea label="내용" variant="solo-filled" placeholder="내용을 입력해주세요." v-model="article.content" rows="10" readonly></v-textarea>
        </v-row>  
        <v-row class="text-center d-flex justify-center align-end" style="height: 170px;">
          <template v-if="userInfo === null">
            <v-btn class="mx-4" @click="moveList">글목록</v-btn>
          </template>
          <template v-else-if="userInfo.userId === article.userId">
            <v-btn class="mx-4" @click="moveList">글목록</v-btn>
            <v-btn class="mx-4" @click="moveModify">글수정</v-btn>
            <v-btn class="mx-4" @click="onDeleteArticle">글삭제 </v-btn>
          </template>
          <template v-else>
            <v-btn class="mx-4" @click="moveList">글목록</v-btn>
          </template>
        </v-row>  
      </v-container>
    </div>
  </div>
</template>

<style scoped></style>
