<script setup>
import { ref} from 'vue'; 
import { getAptDeal } from "@/api/map";
import { httpStatusCode } from "@/util/http-status";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { localAxios } from "@/util/http-commons";
import VChart from "../chart/Vchart.vue"

const local = localAxios();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const snackbar = ref(false); 

const props = defineProps({
  result: Object,
});

const dealHistory = ref([]); 
const filteringDealHistory = ref([]); 
const dealYear = ref([]); 
const toggle = ref([]);
const wholeYear = ref([2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022]); 


const now_lat = ref([props.result.lat]);
const now_lng = ref([props.result.log]);

const chartMaterial = ref({ 
  title: '년도별 거래량',
  categories: wholeYear.value, 
  datas: [], 
})

const getRoadView = (() => {
  // 카카오 객체가 있고 맵을 그릴 준비가 되어있다면 맵 실행
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }

});

const initMap = () => {
  var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
  var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
  var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

  var position = new kakao.maps.LatLng(now_lat.value, now_lng.value);

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  roadviewClient.getNearestPanoId(position, 50, function(panoId) {
      roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
  });
}


const getAptDealInfo = async (result) => {
  console.log("아파트 매매정보 찾자!");

  await getAptDeal(
      result.aptCode,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          // console.log(response.data.aptDealList);
          dealHistory.value = response.data.aptDealList;
          filteringDealHistory.value = response.data.aptDealList;
          dealYear.value = response.data.validYear; 
          toggle.value = []; 
          now_lat.value = result.lat;
          now_lng.value = result.lng;

          // 데이터 함수
          for (let i = 0; i < wholeYear.value.length; i++){
            console.log(dealHistory.value[0].dealYear, wholeYear.value[0]);
            let count = dealHistory.value.filter((deal) => deal.dealYear === wholeYear.value[i]).length;
            chartMaterial.value.datas.push(count); 
          }
        }
      },
      (error) => {
        console.error(error);
      }
  );
  await getRoadView();
}
const filterYear = (toggle) => {
  console.log(toggle)
  if (toggle !== undefined) {
    filteringDealHistory.value = dealHistory.value.filter((item) => item.dealYear === toggle);
    // dealHistory.value = dealHistory.value.filter((item) => ({ return item.dealYear === toggle }));
  } else {
    filteringDealHistory.value = dealHistory.value; 
  }
}

const changeLikeState = (state, item) => { // state : 좋아요 상태, item : 현재 선택된 아파트
  console.log(state); 
  console.log(item.aptCode)
  if (userInfo.value == null) {
    snackbar.value = true; 
    return; 
  } else {
    if (state == 0) {
      console.log("좋아요로 바뀌어야 함")
      local.post(`/apt/fav`,{ userId: userInfo.value.userId, aptCode: item.aptCode })
        .then(() => {
          console.log("favReg 등록 : 성공");
          item.likeStatus = 1; 
        })
        .catch(() => {
          console.log("favReg 등록 : 실패");
        });
    } else {
      console.log("안좋아요 ㅠㅠ")
      local.post(`/apt/fav/${userInfo.value.userId}/${item.aptCode}`)
        .then(() => {
          console.log("favReg 삭제 : 성공"); 
          item.likeStatus = 0; 
        })
        .catch(() => {
          console.log("favReg 삭제 : 실패"); 
        });
    }
  }
}
</script>


<template>
  <!-- <div id="card" class="w_round_bar"></div> -->
  <v-card 
    class="ma-lg-3" 
    width="250" 
    height="140" 
    style="background-color: #E3F2FD;"
    :class="{liked : result.likeStatus == 1 }  "
    :title="props.result.apartmentName"
    :text="`준공년도 : ${props.result.buildYear}`"
    :elevation="17">
    <!-- Modal 창 -->
    <v-dialog width="800">
      <template v-slot:activator="{ props }" >
        <div class="d-flex justify-space-between">
          <v-btn :class="{likedBtn : result.likeStatus == 1 }" v-bind="props" text="거래 정보" style="background-color: #BBDEFB; margin-left: 6%;" @click="getAptDealInfo(result)"> </v-btn>
          <!-- <img class="likeImg mr-3" :src="`src/assets/img/heart/like_${result.likeStatus}.png`" style="width: 40px; height: 40px;" alt=""> -->
        </div>
      </template>
      
      <template v-slot:default="{}">
        <v-card class="for_font">
          <div class="d-flex flex-wrap align-center" >
            <v-card-title class="h_container">
              <div class="ma-3" style="font: 50px gBold;">{{ result.apartmentName }}</div>
              <img class="likeImg" :src="`src/assets/img/heart/like_${result.likeStatus}.png`" @click="changeLikeState(result.likeStatus, result)" style=" display:block; width: 40px; height: 40px;" alt="">
            </v-card-title>
          </div>
          <div class = "separator"></div>
          <!-- 로드뷰 구역 start-->
          <v-card-title class="d-flex align-center flex-column">
            <div class="ml-4" style="font-size: 25px; font-family:'NanumBarun';">로드뷰</div>
            <div id="roadview" style="width:100%;height:300px;"></div>
          </v-card-title>
          <!-- 차트 구역 Start-->
          <v-card-title class="d-flex align-center flex-column">
            <div class="ml-4" style="font-size: 25px; font-family:'NanumBarun';">거래량 분석</div>
          </v-card-title>
          <VChart :chartMaterial="chartMaterial"></VChart>
          <!-- 차트 구역 End-->
          <v-card-title class="d-flex align-center flex-column">
            <div class="ml-4" style="font-size: 25px; font-family:'NanumBarun';">매매내역</div>
            <div style="width:10%"></div>
          </v-card-title>
          <!-- 년도별 필터링 START-->
          <div class="d-flex  align-center mx-6">
            <v-btn-toggle
              v-model="toggle"
              style="font: 15px gMedium;"
            >
              <v-btn v-for="(year) in dealYear" :key="year.value" :value="year" @click="filterYear(toggle)">{{ year }}</v-btn>
            </v-btn-toggle>
          </div>
          <!-- 년도별 필터링 END-->
          <v-card-text>
            <v-virtual-scroll :items="filteringDealHistory" height="400">
              <!-- 매매 Detail -->
              <template v-slot:default="{ item }">
                <v-container class="my-2" style="background-color: lightgray;">
                  <v-row no-gutters class="center-content">
                    <v-col>
                      <v-chip class="ma-2 custom-chip" color="#000" label>🗓️ 매매년월</v-chip>
                    </v-col>
                    <v-col class="custom-font">
                      {{ item.dealYear }}년 {{ item.dealMonth }}월
                    </v-col>
                    <v-col>
                      <v-chip class="ma-2 custom-chip" color="#000" label>💵 가격(만원)</v-chip>
                    </v-col>
                    <v-col class="custom-font">
                      {{ item.dealAmount }}
                    </v-col>
                  </v-row>
                  <v-row no-gutters class="center-content">
                    <v-col>
                      <v-chip class="ma-2 custom-chip" color="#000" label>🏢 평수</v-chip>
                    </v-col>
                    <v-col class="custom-font">
                      {{ item.area }}
                    </v-col>
                    <v-col>
                      <v-chip class="ma-2 custom-chip" color="#000" label>👟 층</v-chip>
                    </v-col>
                    <v-col class="custom-font">
                      {{ item.floor }}
                    </v-col>
                  </v-row>
                </v-container>
              </template>
               <!-- 매매 Detail END-->
            </v-virtual-scroll>
            <br>
          </v-card-text>
        </v-card>
      </template>
    </v-dialog>
    <v-snackbar
      v-model="snackbar"
    >
      로그인이 필요한 서비스입니다.

      <template v-slot:actions>
        <v-btn
          color="pink"
          variant="text"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<style scoped>
.ma-lg-3{
  font-family: 'NanumBarun';
}
.d-flex{
  font-family: 'NanumBarun';
}

.ml-4{
  padding: 10px;
  border: 2px solid black;
}
.separator{
    height: 2px;
    align-self: center;
    width: 95%;
    border: 2px solid black;
    margin-top: 20px;
    margin-bottom: 50px; 
}
#roadview{
  margin: 40px;
}
#deal-detail{
  display: grid;
}
.center-content {
    display: flex;
    justify-content: center;
    align-items: center;
  }
.h_container{
  width: 100%;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
}


.custom-chip {
  height: 40px;
  font: 18px gMedium;
  font-family: 'NanumBarun';
}
.custom-font{
  font: 18px gMedium;
  font-family: 'NanumBarun';
}

.likeImg{
  cursor: pointer;
  margin-left: 10px;
}

.liked{
  background-color: #FFCDD2 !important;
}
.likedBtn{
  background-color: #FFEBEE !important;
}

@font-face{
  font-family: 'NanumBarun';
  font-weight: 800;
  src:url("../../assets/fonts/ttf/NanumBarunGothic-YetHangul.ttf")
}


</style>