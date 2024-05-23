import { createRouter, createWebHistory } from "vue-router";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/views/TheMainView.vue"),
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserRegister.vue"),
        },
        {
          path: "mypage/:userid",
          name: "user-mypage",
          component: () => import("@/components/users/UserMyPage.vue"),
          redirect: { name: "user-mypage-userInfo" },
          children: [
            {
              path: "userInfo",
              name: "user-mypage-userInfo",
              // beforeEnter: onlyAuthUser,
              component: () => import("@/components/users/mypage/modifyUserInfo.vue"),
            },
            {
              path: "mylike",
              name: "user-mypage-mylike",
              component: () => import("@/components/users/mypage/userLike.vue"),
            }
          ],
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/boards/BoardList.vue"),
        },
        {
          path: "view/:noticeNo",
          name: "article-view",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:noticeNo",
          name: "article-modify",
          // beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/mapsrch",
      name: "mapsrch",
      component: () => import("@/views/MapSearchView.vue"),
    },
    {
      path: "/keywordsrch",
      name: "keywordsrch",
      component: () => import("@/views/KeywordSearchView.vue"),
    },
    {
      path: "/chart",
      name: "chart",
      component: () => import("@/components/chart/VChart.vue"),
    },
  ],
});

export default router;
