<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div style="font: 40px gMedium;" class="text-center py-4 mb-4">글수정</div>
      <div class="d-flex justify-center align-center">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
