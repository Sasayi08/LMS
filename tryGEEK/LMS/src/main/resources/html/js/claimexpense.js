function updatePage(id, email, date, amount) {
    let section = document.getElementById("claims_section");
    if (section.style.display === "none") {
        section.style.display = "block";
    }
    let name = email.charAt(0).toUpperCase() + email.split("@")[0].slice(1);

    let tableBody = document.getElementById("claims").getElementsByTagName("tbody")[0];
    let newRow = tableBody.insertRow();

    let fromCell = newRow.insertCell();
    fromCell.classList.add("text");
    fromCell.setAttribute("id", "claim_who_" + id)
    fromCell.appendChild(document.createTextNode(name));

    let dateCell = newRow.insertCell();
    dateCell.classList.add("date");
    dateCell.setAttribute("id", "claim_date_" + id)
    dateCell.appendChild(document.createTextNode(date));

}

claim_form.onsubmit = async (e) => {
  e.preventDefault();
  let claim = {};
  let fd = new FormData(claim_form);
  fd.forEach( (v,k) => {claim[k] = v;});
  console.log(JSON.stringify(claim));

  const options = {
    method: 'POST',
    body: JSON.stringify(claim),
    headers: { 'Content-Type': 'application/json' }
  }

   fetch('/api/claims', options)
     .then(res => res.json())
     .then(res => updatePage(res.id, res.claimFromWho, res.dueDate))
     .catch(err => console.error(err));
};