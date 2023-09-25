from argparse import ArgumentParser

from config import GROUP_NAMES

PREFIX_LIST = ["Given", "When", "Then", "Feature"]


def transform_file(input_file, output_file, suffix):
    with open(input_file, "r") as fin, open(output_file, "w") as out:
        for line in fin:
            line = line.strip()
            match = sum([line.startswith(prefix) for prefix in PREFIX_LIST])
            if match:
                line = f"{line} ({suffix})"
            out.write(line + "\n")
    print("success...")


if __name__ == "__main__":
    parser = ArgumentParser()

    parser.add_argument(
        "--input_folder", type=str, help="The path to the input folder", required=True
    )
    parser.add_argument(
        "--output_folder", type=str, help="The path to the output folder", required=True
    )

    args = parser.parse_args()

    for group_name, feature_name in GROUP_NAMES.items():
        for feature in feature_name:
            args.input_file = f"{args.input_folder}/{feature}.feature"
            args.output_file = f"{args.output_folder}/{feature}.feature"
            args.suffix = f"{group_name.split('-')[-1]}"
            transform_file(args.input_file, args.output_file, args.suffix)
