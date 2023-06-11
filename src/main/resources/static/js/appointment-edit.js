function updateCheckJson (){
    let checkJson
    let checkLineJson
    let checkLine
    checkJson = '['
    $("#check > tr:not(.last)").each(function() {
        checkLine = $(this)
        if (checkJson == '['){
            checkLineJson = '{'
        } else {
            checkLineJson = ',{'
        }
        checkLineJson += '"good_id":' + checkLine.find(".good").val() + ','
        checkLineJson += '"price":' + checkLine.find(".price").val() + ','
        checkLineJson += '"qty":' + checkLine.find(".qty").val() + ''
        checkLineJson += '}'
        checkJson += checkLineJson
    })
    checkJson += ']'
    $('#checkJson').val(checkJson)
}

function updateCheckTotal () {
    let checkLine
    let checkTotal = 0
    $("#check > tr:not(.last)").each(function() {
        checkLine = $(this)
        checkTotal += Number(checkLine.find(".total").val())
    })
    $('#total').val(checkTotal)

}

function addCheckLine (checkLine){
    let newCheckLine = checkLine.clone(true)
    $("#check").append(newCheckLine)
    checkLine.removeClass("last")
}

function delCheckLine (checkLine){
    checkLine.remove()
}

function updateCheckLine (){
    let checkLine = $(this)
    let good_id = checkLine.find(".good")
    let price = checkLine.find(".price")
    let qty = checkLine.find(".qty")
    let total = checkLine.find(".total")

    if (good_id.val() != "0"){
        if (checkLine.hasClass("last")){
            addCheckLine(checkLine)
        }
        let good = $("#"+good_id.val())
        let goodPrice = good.find(".price")
        price.val(goodPrice.val())
        if (qty.val() <= 0){
            qty.val(1)
        }
        total.val(price.val() * qty.val())
    } else {
        delCheckLine(checkLine)
    }

    updateCheckJson()
    updateCheckTotal()
}

$(document).ready(function() {
    console.log( "ready!" )
    $(".last").on('change', updateCheckLine)
    updateCheckTotal()
})