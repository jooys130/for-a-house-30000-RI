<script setup>
import KakaoMap from "@/components/mapsearch/KakaoMap.vue";
import MapSrchRes from "@/components/mapsearch/MapSrchRes.vue";
import { ref, onMounted, watch } from "vue";
import { getApt } from "@/api/map";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const cities = ref();
const guguns = ref();
const dongs = ref();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

// 선택된 시도구군동 정보 -> props로 MapSrcRes로 넘겨줌
const selected = ref(['none', null, null]);

// 사용자 선택에 따라 select option 불러오기
const sendRequest = function (selidx, regcode) {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
  let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
  //시도 정보 업데이트
  if (selidx == 0) {
    fetch(`${url}?${params}`)
      .then((response) => response.json())
      .then((data) => {
        cities.value = data.regcodes;
        // console.log(cities.value);
      });
  } else if (selidx == 1) {
    fetch(`${url}?${params}`)
      .then((response) => response.json())
      .then((data) => {
        //데이터 정제
        data.regcodes.forEach((regcode) => {
          if (regcode.name.split(" ").length == 2) regcode.name = regcode.name.split(" ")[1];
          else regcode.name = regcode.name.split(" ")[1] + " " + regcode.name.split(" ")[2];
        });
        //데이터 guguns에 저장
        guguns.value = data.regcodes;
        // console.log(guguns.value);
      });
  } else if (selidx == 2) {
    fetch(`${url}?${params}`)
      .then((response) => response.json())
      .then((data) => {
        //데이터 정제
        let idx = 2;
        data.regcodes.forEach((regcode) => {
          if (regcode.name.split(" ").length != 3) idx = 3;
          regcode.name = regcode.name.split(" ")[idx];
        });

        dongs.value = data.regcodes;
      });
  }
};
const onSidoChange = function (sido) {
  // console.log(sido);
  sendRequest(1, sido.code.substr(0, 2) + "*00000");
};

const onGugunChange = function (gugun) {
  // console.log(gugun);
  sendRequest(2, gugun.code.substr(0, 5) + "*");
};

onMounted(() => {
  sendRequest(0, "*00000000");
  // console.log(city.value);
});

// 아파트 정보 불러오기
const results = ref([]); 

watch(selected.value, () => {
  const params = {
    sidoName: selected.value[0] == undefined ? null : selected.value[0].name,
    gugunName: selected.value[1] == undefined ? null : selected.value[1].name,
    dongName: selected.value[2] == undefined ? null : selected.value[2].name,
    userId: userInfo.value === null ? null : userInfo.value.userId,
  };
  console.log(params)
  // axios
  if (params.sidoName != null && params.gugunName != null && params.dongName != null) {
    getApt(
      params,
      (success) => {
        results.value = success.data;  
        // console.log(success.data);
        console.log("확인해보자!")
        console.log(results.value);
      },
      (fail) => {
        console.log(fail);
      }
    );
  }
});
</script>

<template>
  <form>
    <div id="inner-form">
      <select
        v-model.lazy="selected[0]"
        class="b_round_bar size_mid"
        @change="onSidoChange(selected[0])"
      >
        <option disabled value="none" selected>시/도 선택</option>
        <option v-for="city in cities" :key="city.value" :value="city">
          {{ city.name }}
        </option>
      </select>
      <select
        v-model.lazy="selected[1]"
        class="b_round_bar size_mid"
        @change="onGugunChange(selected[1])"
      >
      <option disabled value="null">구/군 선택</option>
        <option v-for="gugun in guguns" :key="gugun.value" :value="gugun">{{ gugun.name }}</option>
      </select>
      <select v-model.lazy="selected[2]" class="b_round_bar size_mid">
        <option disabled value="null">동 선택</option>
        <option v-for="dong in dongs"  :key="dong.value"  :value="dong">{{ dong.name }}</option>
      </select>
      <!-- <button class="w_round_bar" @click.prevent="search">검색</button> -->
    </div>
  </form>
  <div id="map_search_container">
    <template v-if="selected.length < 3">                                                               
          <div class="note">
            <p>지역 정보를 입력해주세요</p>
          </div>
    </template>
    <template v-else-if="results.length === 0 && selected.length === 3">
          <div class="note">
            <p>매매정보가 없습니다. </p>
          </div>
    </template>
    <template v-else>
      <KakaoMap :results="results" />
      <MapSrchRes :results="results" />
    </template>
  </div>
</template>

<style scoped>
#map_search_container {
  border-top: 1px rgba(0, 0, 0, 0.3) solid;
  display: flex;
  height: 100%;
  flex-direction: row;
}
form {
  background-color: lightgray;
  text-align: center;
}
#inner-form {
  margin: auto;
  width: 60%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

#map {
  flex: 1;
}
.note{
  display: flex;
  justify-content: center;
  color: white;
  margin: 6% 25%;
  font: 30px gBold;
}
</style>
