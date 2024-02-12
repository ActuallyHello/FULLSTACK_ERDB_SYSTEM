<template>
    <div class="column is-one-third-desktop is-half-tablet is-full-mobile">
        <div class="box" style="background-color: rgb(245, 245, 245) !important;">
            <table class="table is-bordered is-hoverable is-fullwidth has-text-centered mb-2">
                 <thead>
                    <tr>
                        <th colspan="2" class="has-text-centered">
                            {{ table.tableTitle }}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr 
                        v-for="[key, value] in table.pkFieldsMap"
                        :key="key"
                    >
                        <td class="specificator">PK</td>
                        <td>{{ value }}</td>
                    </tr>
                    <tr 
                        v-for="[key, selectedObject] in table.fkFieldsMap"
                        :key="key"
                    >
                        <td class="specificator">FK</td>
                        <td>{{ selectedObject.power }} : {{ selectedObject.title }}</td>
                    </tr>
                    <tr
                        v-for="[key, value] in table.attrFieldsMap"
                        :key="key"
                    >
                        <td class="specificator">ATTR</td>
                        <td>{{ value }}</td>
                    </tr>
                </tbody> 
            </table>
            <div class="buttons is-right m-0">
                <button 
                    @click="clickOnEditPreviewTable" 
                    class="button is-small is-info"
                >
                    Изменить
                </button>
                <button 
                    @click="clickOnDeletePreviewTable" 
                    class="button is-small is-danger"
                >
                    Удалить
                </button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'TablePreviewComponent',
    props: ['table', 'tableId'],
    data() {
        return {
            id: this.tableId
        }
    },
    methods: {
        clickOnDeletePreviewTable() {
            let answer = confirm(`Вы уверены, что хотите удалить таблицу "${this.table.tableTitle}"?`);
            if (answer === true) {
                this.$emit('clickOnDeletePreviewTable', this.id);
            }
        },
        clickOnEditPreviewTable() {
            this.$emit('clickOnEditPreviewTable', this.id, this.table);
        }
    },
}
</script>

<style>

</style>