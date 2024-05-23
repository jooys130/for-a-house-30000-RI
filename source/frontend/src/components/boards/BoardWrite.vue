<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div>
      <div style="font: 40px gMedium;" class="text-center py-4 mb-4">글쓰기</div>
      <div class="d-flex justify-center align-center">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
