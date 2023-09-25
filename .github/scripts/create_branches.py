import os

import git

project_url = "https://github.com/F2023-ECSE223/ecse223-group-project-testing"
branch_prexi = "p"
num_groups = 11

if __name__ == "__main__":
    git.Git(".").clone(project_url)
    os.chdir("ecse223-group-project-testing")
    repo = git.Repo()

    for i in range(1, num_groups + 1):
        branch_name = f"{branch_prexi}{i}"
        print(f"processing {branch_name}")
        repo.git.checkout("-b", branch_name)
        repo.git.push("origin", branch_name)
