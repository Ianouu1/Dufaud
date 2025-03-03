import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

from dictionaries import *

def plot_errors(ax, file_paths, method, type_):
    data = {}

    for file_path in file_paths:
        df = pd.read_csv(file_path)

        if {'Ntotal', 'Error'}.issubset(df.columns):
            for n_total, error in zip(df['Ntotal'], df['Error']):
                if n_total not in data:
                    data[n_total] = []
                data[n_total].append(error)
        else:
            print(f"Colonnes manquantes dans {file_path}, vérifiez le format du fichier.")
            print(f"Colonnes disponibles: {df.columns.tolist()}")

    if data:
        all_n_total = []
        all_medians = []
        for n_total, errors in sorted(data.items()):
            median_error = np.median(errors)
            all_n_total.append(n_total)
            all_medians.append(median_error)
            ax.scatter([n_total] * len(errors), errors, alpha=0.5, label=None)  # Affichage des points individuels
            ax.scatter(n_total, median_error, color='red', s=100, edgecolors='black',
                       label='Médian' if n_total == all_n_total[0] else None)

    ax.set_xscale('log')
    ax.set_yscale('log')
    ax.set_xlabel("Ntotal (log scale)")
    ax.set_ylabel("Error (log scale)")
    ax.set_title(f"Error vs Ntotal ({method} - {'faible' if type_ == 'up' else 'forte'} scalabilité)")
    handles, labels = ax.get_legend_handles_labels()
    if handles:
        ax.legend()
    ax.grid(True, which="both", linestyle="--", linewidth=0.5)

def update_error_plots(method):
    data_dict = {
        "pi": myLaptop["pi"],
        "assignment102": myLaptop["assignment102"],
        "master_worker": myLaptop["master_worker"]
    }

    if method not in data_dict:
        print(f"Method {method} not found in data dictionary.")
        return

    fig, axs = plt.subplots(1, 2, figsize=(12, 5))
    plot_errors(axs[0], data_dict[method]['up'], method, 'up')
    plot_errors(axs[1], data_dict[method]['out'], method, 'out')

    axs[0].set_title(f"{method} - faible scalabilité")
    axs[1].set_title(f"{method} - forte scalabilité")

    plt.tight_layout()
    plt.show()

# Set the backend to 'TkAgg' to avoid the 'tostring_rgb' issue
plt.switch_backend('TkAgg')

# Utilisation de la fonction pour tracer les erreurs
update_error_plots('pi')
update_error_plots('assignment102')
update_error_plots('master_worker')
