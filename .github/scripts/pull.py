import os
import sys
import git
import pathlib
from config import *

if __name__ == '__main__':
    pathlib.Path(LOCAL_TEMP_DIR).mkdir(exist_ok=True)
    os.chdir(LOCAL_TEMP_DIR)

    for project_name in PROJECT_NAMES:
        print(f'processing: {project_name}...')
        git.Git('.').clone(f'{ORG_URL}{project_name}')
    