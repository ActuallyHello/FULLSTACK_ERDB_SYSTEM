<template>
    <div>
        <div class="box mt-5">
            <div class="columns">
                <div class="column is-4">
                    <div class="field has-addons">
                        <div class="control">
                            <input class="input" type="text" placeholder="Название темы">
                        </div>
                        <div class="control">
                            <a class="button is-primary">
                                Поиск
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column"></div>
            </div>
        </div>
        <div class="columns is-multiline mt-5">
            <div class="column is-3" v-for="topic of topics" :key="topic.id" @click="clickOnOpenTopic(topic.title)">
                <div class="box has-text-centered">
                    <p>{{ topic.title }}</p>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios'
import {toast} from 'bulma-toast'

export default {
    name: 'MainView',
    data() {
            return {
                topics: []
            }
        },
    mounted() {
        this.getTopics();
    },
    methods: {
        async getTopics() {
            await axios
                .get(`/models`)
                .then(response => {
                    console.log(response.data);
                    this.topics = response.data.map(model => {
                        return {
                            title: model.topic,
                            id: model.id
                        }
                    });
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при получении модели! Обратитесь к адиминстратору!",
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
        },

        async clickOnOpenTopic(title) {
            this.$router.push(`/models?topic=${title}`);
        },
    }
}
</script>

<style scoped>
</style>
  