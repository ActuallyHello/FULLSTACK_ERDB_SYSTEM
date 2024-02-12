<template>
    <tr :id="id">
        <td class="specificator">PK</td>
        <td>
            <div class="field">
                <p class="control is-expanded">
                    <input 
                        v-model="title" 
                        @change="changePKRow"
                        class="input" 
                        type="text" 
                        placeholder="Название первичного ключа"
                    >
                </p>
            </div>
        </td>
        <td class="specificator has-text-centered">
            <button 
                @click="clickOnDeletePKRow"
                class="delete button is-danger">
            </button>
        </td>
    </tr>
</template>

<script>
export default {
    name: 'PrimaryKeyRow',
    props: ['pkId', 'pkTitle'],
    data() {
        return {
            id: this.pkId,
            title: this.pkTitle,
        }
    },
    methods: {
        clickOnDeletePKRow() {
            this.$emit('clickOnDeletePKRow', this.id);
        },
        changePKRow() {
            this.$emit('changePKRow', this.id, this.title);
        }
    }
}
</script>

<style scoped>
    
</style>