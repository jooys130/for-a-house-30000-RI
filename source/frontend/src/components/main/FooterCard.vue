<script setup>

</script>
<template>
    <div class = "footer_content"> 
      <div class = "footer_menu">
        <a href="next">광고/제휴/기업매물</a>
        <span>|</span>
        <a href="agree">이용약관</a>
        <span>|</span>
        <a href="intro">회사 소개</a>
        <span>|</span>
        <a href="intro">서비스 문의</a>
      </div>
      <div class = "footer_etc">
        <span>(주)전주김밥</span>
        <span>
          |
          싸피 11기 김규리, 김동진, 전용수, 주연수
        </span>
      </div>
    </div>
</template>

<style scoped>
.footer_content{
  margin-top: 100px;
    width: 100%;
    height: 30vh;
    font-family: 'NanumBarun';
    font-weight: bolder;

}


.footer_menu, .footer_etc{
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.footer_menu a, footer_menu span{
  margin: 0 1rem;
}

@font-face{
  font-family: 'NanumBarun';
  font-weight: 800;
  src:url("../../assets/fonts/ttf/NanumBarunGothic-YetHangul.ttf")
}

</style>
