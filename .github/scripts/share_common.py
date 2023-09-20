import os
import sys
import git
import pathlib
from shutil import copytree, ignore_patterns, rmtree
from config import *
from argparse import ArgumentParser

def remove_all(path):
    r = os.listdir(path)
    for f in r:
        filename = os.path.join(path, f)
        if os.path.isfile(filename) and 'README.md' not in filename:
            os.remove(filename)
        elif not os.path.isfile(filename) and not filename.endswith('.git'):
            rmtree(filename)

def should_keep(filename, names):
    for name in names:
        if name in filename:
            return True
    return False

def remove_step_definitions(path, group_files):
    path = os.path.join(path, STEP_DEFINITION_PATH)
    r = os.listdir(path)
    kept_files = COMMON_TEST_FILES + group_files
    for f in r:
        fname = os.path.join(path, f)
        if os.path.isfile(fname) and not should_keep(fname, kept_files):
            os.remove(fname)

if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('--commit_message', type=str, required=True)
    parser.add_argument('--all_step_definitions', default=False, action='store_true')
    parser.add_argument('--no_step_definitions', default=False, action='store_true')
    parser.add_argument('--keep_files', default=False, action='store_true')
    args = parser.parse_args()

    git_token = os.getenv('GIT_TOKEN')

    # do not sync ANY step definitions
    if args.no_step_definitions:
        remove_step_definitions('../..', [])

    auth_url = f'https://{git_token}:x-oauth-basic@github.com/{ORG_NAME}'

    for project_name in GROUP_NAMES:
        print(f'processing: {project_name}...')
        # clone project
        git.Git('.').clone(f'{auth_url}/{project_name}')

        os.chdir(project_name)
        repo = git.Repo('.')
        branches = repo.git.branch('-r')
        if 'origin/' + BRANCH_NAME in branches or BRANCH_NAME in branches:
            repo.git.checkout(BRANCH_NAME)
            repo.git.pull()
        else:
            repo.git.checkout('-b', BRANCH_NAME)


        # remove everything in the folder
        if not args.keep_files:
            remove_all(os.getcwd())

        copytree(TEMPLATE_DIR, os.getcwd(), dirs_exist_ok=True, ignore=ignore_patterns('.git', '.github'))
        
        if not args.all_step_definitions:
            remove_step_definitions(os.getcwd(), GROUP_NAMES[project_name])

        repo.git.add('.')
        repo.config_writer().set_value("user", "name", "ECSE223 bot").release()
        repo.config_writer().set_value("user", "email", "ecse223@mcgill").release()
        try:
            repo.git.commit('-m', args.commit_message)
            repo.git.push('origin', BRANCH_NAME, '-u')
            print(f'{project_name} finished...')
        except git.exc.GitCommandError as e:
            print(f'push failed for {project_name} failed due to {e}')
            print('continue...')
        os.chdir('..')
