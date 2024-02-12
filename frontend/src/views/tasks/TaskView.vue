<template>
    <div class="columns">
        <div class="column is-3">
            <div class="field">
                <label class="label">Задание:</label>
                <div class="control">
                    <input readonly :value="task.title" class="input" type="text" style="width: 500px">
                </div>
            </div>
            <div class="field">
                <label class="label">Сложность:</label>
                <div class="control">
                    <input readonly :value="task.complexity" class="input" type="text">
                </div>
            </div>
            <div class="field">
                <label class="label">Описание задания:</label>
                <div class="control">
                    <textarea readonly class="textarea">{{ task.description }}</textarea>
                </div>
            </div>
            <button class="button" @click="execute">Выполнить</button>
        </div>
        <div class="column">
        </div>
    </div>
    <div class="content mb-1 mt-3">
        <h4>Денормализованный набор данных:</h4>
    </div>
    <div style="width: 100%; overflow: scroll;">
                <table class="table is-bordered is-striped is-fullwidth">
                <thead v-if="header.length != 0">
                    <tr>
                        <th v-for="headCol in header">
                            {{ headCol }}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="row in body">
                        <td v-for="col in row">
                            {{ col }}
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'TaskView',
    data() {
        return {
            task: {},
            header: [],
            body: []
        }
    },
    async mounted() {
        await this.loadTask(this.$route.params.id);
        this.getDataForTask();
    },
    methods: {
        async loadTask(id) {
            await axios
                .get(`tasks/${id}`)
                .then(response => {
                    this.task = response.data.task;
                })
                .catch(
                    error => console.log("ERROR", error, error.response)
                )
            console.log("THIS IS TASK DATA...", this.task.data)
        },
        getDataForTask() {
            if (this.task.data) {
                console.log(this.task.data);
                if (this.task.complexity == 'легко') {
                    this.header = this.task.data[0];
                    this.body = this.task.data.splice(1, this.task.data.length);
                } else {
                    this.body = this.task.data;
                }
            }
        },
        execute() {
            this.$router.push('/create-model?task_id='+this.task.id)
        }
    },
}
</script>

<style scoped>
    td {
        white-space: nowrap;
    }
</style>