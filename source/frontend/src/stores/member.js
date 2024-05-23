import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  regist,
  modify,
  deleteUser,
  likesearchAll,
  chatGpt
} from "@/api/user";
import { getAptNameByCode } from "@/api/map";
import { httpStatusCode } from "../util/http-status";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const router = useRouter();
    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);
    const userLikedArray = ref([]);
    const aptCodes = ref([]);
    const likes = ref([]);
    const chatGptResponse= ref("")
    const userLogin = async (loginUser) => {
      await userConfirm(
        loginUser,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response;
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
          } else {
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const getUserInfo = (token) => {
      let decodeToken = jwtDecode(token);
      findById(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userInfo.value = response.data.userInfo;
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.error(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          isValidToken.value = false;

          await tokenRegenerate();
        }
      );
    };

    const tokenRegenerate = async () => {
      await tokenRegeneration(
        JSON.stringify(userInfo.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
                router.push({ name: "user-login" });
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                userInfo.value = null;
              }
            );
          }
        }
      );
    };

    const userLogout = async (userid) => {
      await logout(
        userid,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            userInfo.value = null;
            isValidToken.value = false;
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
          } else {
            console.error("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const userRegist = async (registUser) => {
      console.log("(**) test" + registUser.userInfo);
      await regist(
        registUser,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            console.log("회원가입 완료");
          } else {
            console.log("회원가입 실패");
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const userModify = async (modifyUser) => {
      console.log(modifyUser);
      await modify(
        modifyUser,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log("회원정보 수정 완료");
          } else {
            console.log("회원정보 수정 실패");
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const userDelete = async (userid) => {
      console.log(userid);
      try {
        await deleteUser(userid);
        console.log("??");
        isLogin.value = false;
        userInfo.value = null;
        isValidToken.value = false;
        sessionStorage.removeItem("accessToken");
        sessionStorage.removeItem("refreshToken");
        // await userLogout(userid);
        router.push({ name: "home" });
      } catch (error) {
        console.error(error);
      }
    };

    const userLike = async (userid) => {
      console.log(userid + "member-js!");
      try {
        await likesearchAll(
          userid,
          (response) => {
            userLikedArray.value = response.data;
            console.log(userLikedArray.value);
            aptCodes.value = userLikedArray.value.map(item => item.aptCode)
            .filter((value, index, self) =>
              self.indexOf(value) === index);
            getNameAll(aptCodes);
          },
          (error) => {
            console.error(error);
          }
        );
      } catch (error) {
        console.error(error);
      }
    };

    const getNameAll = async (aptCodes) => {
      // 아파트 코드 기준으로 아파트 이름 가져오기
      console.log("getNameAll func >>>>>>>>>>");
      console.log(aptCodes.value);
      let arr = aptCodes.value.map(Number);
      console.log(arr);
      arr = arr.join(",");
      try {
        getAptNameByCode(
          arr,
          ({ data }) => {
            console.log("!!")
            console.log(data);
            likes.value = data;
          },
          (error) => {
            console.error(error);
          }
        );
      } catch (error) {
        console.error(error);
      }
    };
    
    const responseByGPT = async (question) => {
      console.log("responseByGPT func >>>>>>>>>>");
      console.log(question);
      await chatGpt(
        question,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log(response.data.choices[0].text);
            chatGptResponse.value = response.data.choices[0].text;
          } else {
            console.log("gpt한테 물어보기 실패");
          }
        
        },
        (error) => {
          console.error(error);
        }
      );
    };

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
      userRegist,
      userModify,
      userDelete,
      userLike,
      getNameAll,
      responseByGPT,
      userLikedArray,
      aptCodes,
      likes,
      chatGptResponse
    };
  },
  { persist: true }
);
