<script setup>

</script>

<template>
    <div class="container">
        <div class = "left"> 
            <div class="img_area">
                <a href="https://new-m.pay.naver.com/loan/rental/input/houseInfo" class="img_disp">
                    <img src = "../../assets/img/page/loan-page.jpg" width="40%">
                </a>
                <a href="https://www.mk.co.kr/news/realestate" class="img_disp">
                    <img src = "../../assets/img/page/news-page.jpg" width="40%">
                </a>
            </div>
        </div>
        <div class = "notice_area"> 
            <div class = "board_area">
                <h3 class = "tit">
                    공지사항
                </h3>
                <div class = "separator"></div>
                <ul class = "notice_list">
                    <li class = "list">
                        <a href="http://localhost:5173/board/view/1" class="txt"> 안녕하세요 개발자입니다.</a>
                        <span class="day">2024.05.01</span>
                    </li>
                    <li class = "list">
                        <a href="http://localhost:5173/board/view/2" class="txt"> 업데이트(2024-05-20)에 관한 공지사항입니다.</a>
                        <span class="day">2024.05.20</span>
                    </li>
                    <li class = "list">
                        <a href="http://localhost:5173/board/view/3" class="txt"> [이달의 부동산 소식] 메뉴가 활성화 되었습니다.</a>
                        <span class="day">2024.05.23</span>
                    </li>
                    
                </ul>
            </div>
        </div>
    </div>

</template>

<style scoped>
.container{
    display: flex;
    font-size: large;
}

.left, .notice_area {
flex: 1;
height: 30vh;

}

.left {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.img_area{
    margin-left: 80px;
}
.notice_area{
    display: flex;
    justify-content: center;
    align-items: center;
    
}

.board_area{
    width: 80%;
    height: 80%;
    padding: 30px;
    border: 2px solid lightgray;
}
.img_area .img_disp{
    width: 90%;
    height: 80%;
    padding: 30px;
}
.separator{
    height: 2px;
    width: 50px;
    background-color: lightgray;
    margin-top: 10px;
}
.notice_list .list{
    margin: 10px 0px;
}

.list{
    display: flex;
    justify-content: space-between;
    align-items: center;
}

</style>