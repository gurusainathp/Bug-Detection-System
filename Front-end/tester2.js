document.getElementById('bug-report-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const project = document.getElementById('project').value;
    const bugTitle = document.getElementById('bug-title').value;
    const description = document.getElementById('description').value;
    const priority = document.getElementById('priority').value;

    const bug = { project, bugTitle, description, priority };

    const bugs = JSON.parse(localStorage.getItem('bugs')) || [];

    bugs.push(bug);

    localStorage.setItem('bugs', JSON.stringify(bugs));

    alert('Bug Reported Successfully');

    this.reset();
});
