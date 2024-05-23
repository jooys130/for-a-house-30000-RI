<template>
    <div>
      <highcharts class="hc" :options="chartOptions" ref="chart"></highcharts>
    </div>
  </template>
  
  <script>
  import {  onMounted } from "vue";
  import Highcharts from 'highcharts'
  import exportingInit from 'highcharts/modules/exporting'
  
  exportingInit(Highcharts);
  
  export default {
      props: {
      chartMaterial: Object, // chartMaterial이라는 props 추가
      },
    setup(props) {
    onMounted(() => {
      console.log(props.chartMaterial.datas);
      // 여기에 초기화 또는 비동기 작업을 수행할 수 있습니다.
    });
    },
    data() {
        //   const datas = ref([1, 2, 3, 4, 5, 6, 7, 8]);

      return {
        chartOptions: {
          title: {
            text: this.chartMaterial.title // 원하는 차트 제목으로 변경
          },
        //   subtitle: {
        //     text: '차트 부제목', // 원하는 차트 부제목으로 변경
        //   },
          xAxis: {
            categories: this.chartMaterial.categories, // 가로축의 라벨을 원하는 값으로 변경
            title: {
              text: '년도별 / 월별', // 가로축의 제목을 원하는 값으로 변경
            },
          },
          yAxis: {
            title: {
              text: '거래량', // 세로축의 제목을 원하는 값으로 변경
            },
          },
          series: [
            {
              data: this.chartMaterial.datas,
            },
          ],
        },
      };
    },
  };
  </script>