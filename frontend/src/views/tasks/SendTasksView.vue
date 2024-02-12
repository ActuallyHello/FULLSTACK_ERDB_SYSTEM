<template>
    <div class="columns">
        <div class="column">
            <div class="subtitle">
                Задания:
            </div>
            <ul>
                <li class="mb-3" v-for="task in tasks" :key="task.id">
                    <div class="card" style="max-width: 500px;">
                        <header class="card-header">
                            <p class="card-header-title">
                                {{ task.title }} {{ task.id }}<br>
                                {{ task.description }}<br>
                                {{ task.complexity }}
                            </p>
                            <label class="checkbox card-header-icon">
                                <input type="checkbox" :value="task.id" v-model="checkedTasks">
                            </label>
                        </header>
                        <footer class="card-footer">
                            <a @click="delete_task(task.id)" class="card-footer-item">Удалить</a>
                        </footer>
                    </div>
                </li>
            </ul>
        </div>
        <div class="column">
            <div class="field">
                <label class="label">Группа студентов:</label>
                <div class="control">
                    <div class="select">
                        <select v-model="groupSelected">
                            <option v-for="(group, index) in groups" :value="group" :selected="index === 0">{{ group }}</option>
                        </select>
                    </div>
                </div>
            </div>
            <ul>
                <li class="mb-3" v-for="student in studentsByGroup" :key="student.id">
                    <div class="card" style="max-width: 500px;">
                        <header class="card-header">
                            <p class="card-header-title">
                                {{ student.first_name }} {{ student.last_name }} {{ student.middle_name }}<br>
                                {{ student.group }}
                            </p>
                            <label class="checkbox card-header-icon">
                                <button class="button" @click="send(student.id)">Отправить</button>
                            </label>
                        </header>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
export default {
    name: 'SendTasksView',
    data() {
        return {
            checkedTasks: [],
            tasks: [],
            students: [],

            groupSelected: '',
            groups: new Set(),
        }
    },
    mounted() {
        this.loadTasks();
        this.loadStudents();
    },
    computed: {
        // getGroups() {
        //     const groups = new Set(this.students.map(student => student.group));
        //     this.groupSelected = groups[0];
        //     return groups;
        // },
        studentsByGroup() {
            return this.students.filter(student => student.group == this.groupSelected)
        }
    }, 
    methods: {
        async loadTasks() {
            await axios
                .get(`tasks/`)
                .then(response => {
                    this.tasks = response.data.tasks;
                })
                .catch(error => console.log("ERROR", error, error.response))
        },
        async loadStudents() {
            await axios
                .get(`persons/students/`)
                .then(response => {
                    this.students = response.data.students;
                    this.groups = new Set(this.students.map(student => student.group));
                    this.groupSelected = this.groups[0];
                })
                .catch(error => console.log("ERROR", error, error.response.data))
        },
        async send(id) {
            const formData = {
                task_id_list: this.checkedTasks,
                student_id_list: [id],
                teacher_id: 1// TODO this.$store.state.user.teacher_id
            }

            await axios
                .post(`tasks/send-tasks/`, formData)
                .then(response => console.log(response.data, "WE SENT TASK SUCCESS", formData))
                .catch(error => console.log(error))
        },
        async delete_task(id) {
            await axios
                .post(`tasks/delete-task/${id}/`)
                .then(response => {
                    this.tasks = this.tasks.filter(item => item.id != id);
                })
                .catch(error => console.log(error))
        }
    },
}
</script>

<style scoped></style>
  