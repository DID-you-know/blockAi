<template>
  <div>
    <Navbar />
    <main class="home body">
      <div class="intro">
        <h1 class="nanum fs-4">인증의 모든 것</h1>
        <h1 class="nanum fs-4">
          <span class="text-secondary">block</span>
          <span class="text-primary">AI</span>
          <span>에서 쉽고 간편하게</span>
        </h1>
        <p class="fs-1">
          블록아이는 블록체인의 DID와 안면인식, 음성인식을 병행한 보안 강화 본인
          인증 솔루션으로, ...
        </p>
        <Button value="신원증명발급" @click="pushStatus" />
      </div>
      <div class="image-container">
        <img src="@/assets/image/main.png" alt="" />
      </div>
    </main>
  </div>
</template>

<script>
import Button from "@/components/Button";
import Navbar from "@/components/Navbar";
import { useRouter } from "vue-router";

export default {
  name: "Home",
  components: {
    Button,
    Navbar,
  },
  setup() {
    const router = useRouter();
    const pushStatus = () => router.push({ name: "status" });

    return {
      pushStatus,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/style/color.scss";

.home {
  display: flex;
  padding: 2rem 3rem 0;
  background-image: linear-gradient($white, $white, $tertiary);

  .intro {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
}
.intro {
  flex: 3;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h1 {
    margin: 1rem;
  }

  .image-container {
    flex: 1;
    width: 100%;
    position: relative;

    img {
      min-width: 850px;
      width: 100%;
      height: auto;
      position: absolute;
      bottom: 0;
    }
  }
}
</style>
