<template>
    <tr>
        <td class="specificator">ATTR</td>
        <td>
            <div class="field">
                <p class="control is-expanded">
                    <input 
                        v-model="title" 
                        @change="changeOnAttrRow"
                        class="input" 
                        type="text" 
                        placeholder="Название атрибута"
                    >
                </p>
            </div>
        </td>
        <td class="specificator has-text-centered">
            <button 
                @click="clickOnDeleteAttrRow"
                class="delete button is-danger">
            </button>
        </td>
    </tr>
</template>

<script>
export default {
    name: 'AttributeRow',
    props: ['attrId', 'attrTitle'],
    data() {
        return {
            id: this.attrId,
            title: this.attrTitle
        }
    },
    methods: {
        clickOnDeleteAttrRow() {
            this.$emit('clickOnDeleteAttrRow', this.id);
        },
        changeOnAttrRow() {
            this.$emit('changeOnAttrRow', this.id, this.title);
        }
    }
}
</script>

<style scoped>
    
</style>