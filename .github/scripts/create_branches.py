import os

import git
from tqdm import tqdm

project_url = "https://github.com/F2023-ECSE223/ecse223-group-project-testing"
branch_prexi = "p"
num_groups = 16

if __name__ == "__main__":
    git.Git(".").clone(project_url)
    os.chdir("ecse223-group-project-testing")
    repo = git.Repo()

    for i in tqdm(range(1, num_groups + 1), desc="Creating branches"):
        branch_name = f"{branch_prexi}{i}"
        print(f"processing {branch_name}")
        repo.git.checkout("-b", branch_name)
        repo.git.push("origin", branch_name)

    print("Done")
