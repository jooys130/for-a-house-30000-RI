<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/notice";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  console.log(userInfo.value);
});

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: userInfo.value.userId,
  userName: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { noticeNo } = route.params;
  getModifyArticle(
    noticeNo,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form style="width: 50vw;" @submit.prevent="onSubmit">
    <v-sheet >
      <div>
        <v-text-field label="제목" variant="solo-filled" placeholder="제목을 입력해주세요." v-model="article.subject"></v-text-field>
      </div>
      <div>
        <v-text-field label="작성자" variant="solo-filled" v-model="article.userId" readonly=""></v-text-field>
      </div>
      <div>
        <v-textarea label="내용" variant="solo-filled" placeholder="내용을 입력해주세요." v-model="article.content" rows="10"></v-textarea>
      </div>
      <div class="col-auto text-center d-flex justify-center">
        <v-btn class="mx-8" @click="onSubmit" v-if="type === 'regist'">글작성</v-btn>
        <v-btn class="mx-8" @click="onSubmit" v-else>글수정</v-btn>
        <v-btn class="mx-8" @click="moveList">글목록 </v-btn>
      </div>
    </v-sheet>
  </form>
</template>

<style scoped>
</style>
