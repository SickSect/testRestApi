function getIndex(list, id){
    for (var i = 0; i < list.length; i++){
        if (list[i].id === id){
            return i;
        }
    }
    return -1;
}

var msgApi = Vue.resource('/message{/id}');

Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data: function(){
        return{text:'', id:''}
    },
    watch: {
        messageAttr: function(newVal, oldVal){
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write here" v-model="text"/>' +
            '<input type="button" value="save" v-on:click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var message = {text:this.text};
            if(this.id){
                msgApi.update({id: this.id}, message).then(result=>{result.json().then(data=>{
                    var index = getIndex(this.messages, data.id);
                    this.messages.splice(index, 1, data);
                    this.text = '';
                    this.id = '';
                })})
            }
            else
                msgApi.save({}, message).then(result=> result.json().then(data=>
                {
                    this.messages.push(data);
                    this.text = '';
                }))
        }
    }
})

Vue.component('message-row', {
    props: ['message', 'editMsg', 'messages'],
    template: '<div>' +
            '<i>({{ message.id }}) </i> {{ message.text }}' +
            '<span style="position: absolute; right: 0">' +
                '<input type="button" value="edit" v-on:click="edit"/>' +
                '<input type="button" value="X" v-on:click="del"/>' +
            '</span>' +
        '</div>',
    methods: {
        edit: function (){
            this.editMsg(this.message);
        },
        del: function (){
            msgApi.remove({id: this.message.id}).then(result=>{
                    if (result.ok){
                        this.messages.splice(this.messages.indexOf(this.message), 1);
                    }
                }
            )
        }
    }
});

Vue.component('messages-list', {
    props: ['messages'],
    data: function (){
        return {message:null}
    },
    template:
        '<div style="position relative; width: 300px;">' +
            '<message-form :messages="messages" :messageAttr="message"/>' +
            '<message-row v-for="message in messages" :key="message.id"' +
            ':message="message" :editMsg="editMsg" :messages="messages"/>' +
        '</div>',
    created: function (){
        msgApi.get().then(result =>
        result.json().then(data=>
        data.forEach(message => this.messages.push(message))))
    },
    methods:{
        editMsg: function (message){
            this.message = message;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data:{
        messages: []
    }
});