const bugs = [
    { id: 1, projectName: "Project Alpha", title: "Login issue" },
    { id: 2, projectName: "Project Beta", title: "UI glitch on dashboard" }
];


document.addEventListener("DOMContentLoaded", () => {
    loadProjectData();
});

async function loadProjectData() {
    try {
        const response = await fetch('data.json');
        const data = await response.json();
        populateProjectDropdown(data.projects);
    } catch (error) {
        console.error("Failed to load project data:", error);
    }
}

function populateProjectDropdown(projects) {
    const projectSelect = document.getElementById('project-select');
    const bugSelect = document.getElementById('bug-select');

    projects.forEach((project, index) => {
        let option = document.createElement('option');
        option.value = index;
        option.text = project.projectName;
        projectSelect.appendChild(option);
    });

    projectSelect.addEventListener('change', function () {
        populateBugDropdown(projects[this.value].bugs);
    });

    // Load bugs for the first project by default
    populateBugDropdown(projects[0].bugs);
}

function populateBugDropdown(bugs) {
    const bugSelect = document.getElementById('bug-select');
    bugSelect.innerHTML = ''; // Clear existing options

    bugs.forEach(bug => {
        let option = document.createElement('option');
        option.value = bug;
        option.text = bug;
        bugSelect.appendChild(option);
    });
}

// Load bugs into the table
function loadBugs() {
    const bugsList = document.getElementById('bugs-list');

    bugs.forEach(bug => {
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${bug.projectName}</td>
            <td>${bug.title}</td>
            <td><button class="button" onclick="resolveBug(${bug.id})">Mark as Resolved</button></td>
        `;
        bugsList.appendChild(row);
    });
}

// Resolve bug
function resolveBug(bugId) {
    alert("Bug ID " + bugId + " marked as resolved!");
    // Additional logic to update the bug status can be added here
}

// Initialize the page
loadBugs();
