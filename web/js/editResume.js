function addListeners(buttonsClass, listener) {
    let buttons = document.getElementsByClassName(buttonsClass);
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener("click", listener);
    }
}

addListeners("add_item", addItem);
addListeners("rm_item", removeItem);
addListeners("add_org", addOrganization);
addListeners("rm_org", removeOrg);
addListeners("add_pos", addPosition);
addListeners("rm_pos", removePosition);


function removeOrg() {
    let sectionDiv = this.closest(".section_div");
    sectionDiv.querySelector(".add_org").disabled = false;
    this.closest(".org_div").remove();
    resetOrgContainer(sectionDiv);
}

function removePosition() {
    let orgDiv = this.closest(".org_div");
    orgDiv.querySelector(".add_pos").disabled = false;
    this.closest(".pos_div").remove();
    resetPositionContainer(orgDiv);
}


function addPosition() {
    let orgDiv = this.closest(".org_div");
    let fstPosRmBtn = orgDiv.querySelector(".rm_pos");
    fstPosRmBtn.removeEventListener("click", clearFields);
    fstPosRmBtn.addEventListener("click", removePosition);
    let positionCount = orgDiv.querySelectorAll(".pos_div").length;
    let newPositionDiv = emptyPosDiv.cloneNode(true);
    newPositionDiv.id = orgDiv.id + "." + positionCount;
    setPositionDivInputsAttr(newPositionDiv);
    orgDiv.querySelector(".pos_container").appendChild(newPositionDiv);
    if (positionCount === 4) {
        this.disabled = true;
    }
}

function clearFields() {
    let fields = this.closest("div").querySelectorAll("textarea, input[type='text']");
    for (let i = 0; i < fields.length; i++) {
        fields[i].value = "";
    }
}

function resetOrgContainer(sectionDiv) {
    let orgDivs = sectionDiv.querySelectorAll(".org_div");
    for (let i = 0; i < orgDivs.length; i++) {
        orgDivs[i].id = sectionDiv.id + "." + i;
        resetOrgDiv(orgDivs[i]);
    }
    if (orgDivs.length === 1) {
        let btn = sectionDiv.querySelector(".rm_org");
        btn.removeEventListener("click", removeOrg);
        btn.addEventListener("click", clearFields);
    }
}

function resetPositionContainer(orgDiv) {
    let posDivs = orgDiv.querySelectorAll(".pos_div");
    for (let i = 0; i < posDivs.length; i++) {
        posDivs[i].id = orgDiv.id + "." + i;
        setPositionDivInputsAttr(posDivs[i]);
    }
    if (posDivs.length === 1) {
        let btn = posDivs[0].querySelector(".rm_pos");
        btn.removeEventListener("click", removePosition);
        btn.addEventListener("click", clearFields);
    }
}

function resetOrgDiv(orgDiv) {
    let prefix = orgDiv.id;
    orgDiv.querySelector(".org_name").name = prefix + ".name";
    orgDiv.querySelector(".org_url").name = prefix + ".url";
    orgDiv.querySelector(".add_pos").addEventListener("click", addPosition);
    orgDiv.querySelector(".rm_org").addEventListener("click", removeOrg);
    resetPositionContainer(orgDiv);
}

function setPositionDivInputsAttr(positionDiv) {
    positionDiv.querySelector(".pos_title").name = positionDiv.id + ".title";
    positionDiv.querySelector(".pos_description").name = positionDiv.id + ".description";
    positionDiv.querySelector(".pos_start").name = positionDiv.id + ".start";
    positionDiv.querySelector(".pos_end").name = positionDiv.id + ".end";
    positionDiv.querySelector(".rm_pos").addEventListener("click", removePosition);
}

function addOrganization() {
    let section = this.closest(".section_div");
    let rmOrgBtn = section.querySelector(".rm_org");
    rmOrgBtn.removeEventListener("click", clearFields);
    rmOrgBtn.addEventListener("click", removeOrg);
    let orgContainer = section.querySelector(".org_container");
    let orgCount = orgContainer.querySelectorAll(".org_div").length;
    let newOrgDiv = emptyOrgDiv.cloneNode(true);
    newOrgDiv.id = section.id + "." + orgCount;
    resetOrgDiv(newOrgDiv);
    orgContainer.appendChild(newOrgDiv);
    if (orgCount === 4) {
        this.setAttribute("disabled", "disabled");
    }
}


function addItem(sectionType) {
    sectionType = this.name;
    console.log(sectionType);
    let ul = document.getElementById(sectionType + "ul");
    if (ul.childElementCount >= 10) {
        alert("Добавлено максимально допустимое количество элементов");
        return;
    }
    let li = document.createElement("li");
    let input = document.createElement("input");
    input.setAttribute('type', 'text');
    input.setAttribute('name', sectionType);
    let btn = document.createElement("button");
    btn.setAttribute("name", sectionType);
    btn.innerHTML = "Minus";
    btn.addEventListener("click", removeItem);
    let str = ul.appendChild(li);
    str.appendChild(input)
    str.appendChild(btn);

    console.log(ul.childElementCount);
}

function removeItem() {
    let removedItem = this.parentElement;
    removedItem.remove();
}

const emptyOrgDivHtml =
    "                        <dl>\n" +
    "                            <dt>Название учреждения:</dt>\n" +
    "                            <dd><input class=\"org_name\" type=\"text\" name=\"EXPERIENCE.0\" value=\"\" required>\n" +
    "                            </dd>\n" +
    "                        </dl>\n" +
    "                        <dl>\n" +
    "                            <dt>Сайт учреждения:</dt>\n" +
    "                            <dd><input class=\"org_url\" type=\"text\" name=\"EXPERIENCE.0.url}\" value=\"\">\n" +
    "                            </dd>\n" +
    "                        </dl>\n" +
    "                        <h3><span>Занимаемые позиции:</span>&nbsp;\n" +
    "                            <input class=\"add_pos\" type=\"button\" value=\"add pos\"></h3>\n" +
    "                        \n" +
    "                            \n" +
    "                           <div class=\"pos_container\">" +
    "                            <div class=\"pos_div\">\n" +
    "                                <dl>\n" +
    "                                    <dt>Position</dt>\n" +
    "                                    <dd><input class=\"pos_title\" type=\"text\" required=\"\" name=\"EXPERIENCE.0.0.title\" value=\"\"></dd>\n" +
    "                                </dl>\n" +
    "                                <dl>\n" +
    "                                    <dt>Description</dt>\n" +
    "                                    <dd>\n" +
    "                                            <textarea class=\"pos_description\" name=\"EXPERIENCE.0.0.description\" rows=\"5\" cols=\"75\">                                            </textarea>\n" +
    "                                    </dd>\n" +
    "                                </dl>\n" +
    "                                <dl>\n" +
    "                                    <dt>Since</dt>\n" +
    "                                    <dd><input class=\"pos_start\" type=\"text\" required=\"\" name=\"EXPERIENCE.0.0.startDate\" value=\"\" placeholder=\"MM-yyyy\"></dd>\n" +
    "                                </dl>\n" +
    "                                <dl>\n" +
    "                                    <dt>Until</dt>\n" +
    "                                    <dd><label title=\"Оставьте поле пустым, если продолжаете занимать эту должность\">\n" +
    "                                        <input type=\"text\" class=\"pos_end\" name=\"EXPERIENCE.0.0.endDate\" value=\"\" placeholder=\"MM-yyyy\">\n" +
    "                                    </label></dd>\n" +
    "                                </dl>\n" +
    "                                <input class=\"rm_pos\" type=\"button\" value=\"Удалить позицию\">\n" +
    "                            </div>\n" +
    "                        \n" +
    "                        </div>" +
    "                        <input class=\"rm_org\" type=\"button\" value=\"Удалить организацию\" style=\"display: block; margin: auto\">"


const emptyOrgDiv = document.createElement("div");
emptyOrgDiv.className = "org_div";
emptyOrgDiv.innerHTML = emptyOrgDivHtml;

const emptyPosDiv = emptyOrgDiv.querySelector(".pos_div");
