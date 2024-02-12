<template>
    <div>
        <section class="hero is-small is-primary has-text-centered mb-3 mt-2">
            <div class="hero-body">
                <p class="subtitle">
                    Модель: {{ modelTitle }} # {{ modelId }}
                </p>
            </div>
        </section>
        <div class="columns mt-5">
            <div class="column">
                <div class="box">
                    <p><label class="label">Название модели:</label> {{ modelTitle }}</p>
                    <hr>
                    <p><label class="label">Описание:</label>  {{ modelDescription }} </p>
                </div>
            </div>
            <div class="column">
                <div class="box">
                    <p><label class="label">Бизнес-процесс:</label> {{ modelTopic }}</p>
                    <p><label class="label">Автор:</label> {{ author }} {{ authorType }}</p>
                    <p><label class="label">Дата создания:</label> {{ modelUpdatedAt }}</p>
                </div>
            </div>
        </div>
        <div class="box">
            <div class="subtitle">
                    Сущности:
                </div>
            <div class="columns is-multiline">
                <div class="column is-3" v-for="entity in entities">
                    <table class="table is-bordered is-striped is-hoverable is-fullwidth has-text-centered">
                        <tbody>
                            <tr>
                                <td colspan="2">{{ entity.title }}</td>
                            </tr>
                            <tr v-for="attr in entity.attributeDTOList">
                                <td class="specificator">{{ attr.attributeType }}</td>
                                <td class="field-title">{{ attr.title }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="box">
            <div style="width: 100%;" class="mb-3">
                <div class="subtitle">
                    Матрица смежности:
                </div>
                <div style="overflow: scroll;">
                    <table class="table is-bordered is-striped has-text-centered ml-3 mb-4">
                        <thead>
                            <tr>
                                <td></td>
                                <td class="is-size-6" v-for="head in entities">
                                    {{ head.title }}
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="row in matrix">
                                <td class="is-size-6">{{ row.entity }}</td>
                                <td class="is-size-6" v-for="relation in row.relations">
                                    {{ relation }}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import { toast } from 'bulma-toast'

export default {
    name: 'DetailModelView',
    data() {
        return {
            modelId: 0,
            modelTitle: '',
            modelDescription: '',
            modelTopic: '',
            modelUpdatedAt: Date(),
            author: '',
            authorType: '',

            entities: [],
            matrix: [],
        }
    },
    mounted() {
        this.loadModelById(this.$route.params.id);
    },
    methods: {
        collectMatrix(entities, relations) {
            const matrix = [];
            for (let head of entities) {
                const powers = [];
                for (let col of entities) {
                    let power = '-';
                    for (let relation of relations) {
                        if (head.title == relation.fromEntity && col.title == relation.toEntity || head.title == relation.toEntity && col.title == relation.fromEntity) {
                            power = relation.power;
                            break;
                        }
                    }
                    powers.push(power);
                }
                matrix.push({
                    entity: head.title,
                    relations: powers
                });
            }
            return matrix;
        },

        convertPersonType(personType) {
            return personType == "TEACHER" ? "Преподаватель"
                : personType == "STUDENT" ? "Студент"
                : "Не определён";
        },

        loadModelById(id) {
            axios
                .get(`/models/${id}`)
                .then(response => {
                    this.modelId = response.data.id;
                    this.modelTitle = response.data.title;
                    this.modelDescription = response.data.description;
                    this.modelTopic = response.data.topic;
                    this.modelUpdatedAt = response.data.updatedAt;

                    this.author = response.data.personDTO.lastName + " " + response.data.personDTO.firstName;
                    this.authorType = this.convertPersonType(response.data.personDTO.personType);

                    this.entities = response.data.modelEntityDTOList;
                    const relations = response.data.relationDTOList;
                    this.matrix = this.collectMatrix(this.entities, relations);
                })
                .catch(error => {
                    console.log(error)
                    this.error = true;
                });
        },
    }
}
</script>

<style scoped>
    .card-text {
        height: 70px;
        overflow-y: hidden;
    }

    .specificator {
        width: 50px;
    }

    .field-title {
        max-width: 190px !important;
        overflow: hidden;
    }
</style>
  