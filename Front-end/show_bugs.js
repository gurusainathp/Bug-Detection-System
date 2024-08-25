document.addEventListener('DOMContentLoaded', function() {

    const bugs = JSON.parse(localStorage.getItem('bugs')) || [];

    const bugsTableBody = document.querySelector('#bugs-table tbody');

    bugs.forEach(bug => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${bug.project}</td>
            <td>${bug.bugTitle}</td>
            <td>${bug.description}</td>
            <td>${bug.priority}</td>
        `;
        bugsTableBody.appendChild(row);
    });
});
