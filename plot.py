import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.widgets import Button
import matplotlib

from dictionaries import myLaptop, G26D4

matplotlib.use('TkAgg')

# Constantes
scale_up = "up"
scale_out = "out"

# Variables globales pour le dataset et la méthode sélectionnés
current_dataset = "myLaptop"
current_method = "pi"


# Fonction pour lire un fichier CSV et tracer la courbe du speedup
def plot_speedup(ax, csv_file, avoidMultipleSp, scalabilite):
    # Charger les données
    df = pd.read_csv(csv_file)

    # Calculer la moyenne des temps pour chaque nombre de Nworkers
    df_avg = df.groupby('Nworkers')['Time'].mean().reset_index()

    # Extraire les valeurs utiles
    nWorkers = df_avg['Nworkers']
    durations = df_avg['Time']

    # Calculer le speedup
    t1 = durations.iloc[0]  # Temps d'exécution pour 1 worker
    speedup = t1 / durations

    # Raccourcir les noms des fichiers
    workload = csv_file.split('_')[-1].split('.')[0]

    # Tracer la courbe
    if not avoidMultipleSp:
        if scalabilite == scale_up:
            ax.plot(nWorkers, nWorkers, linestyle='--', color='r', label='Speedup linéaire (Sp=p)')
        elif scalabilite == scale_out:
            ax.plot(nWorkers, [1] * len(nWorkers), linestyle='--', color='r', label='Speedup linéaire (Sp=1)')
            ax.set_ylim(0, 1.4)
        else:
            print("le paramètre 'scalabilite' mal renseigné")
    ax.plot(nWorkers, speedup, marker='o', linestyle='-',
            label=f'Speedup mesuré pour une charge initiale de {workload}/coeurs')


def plot_average_speedup(ax, datasets, method, avoidMultipleSp, scalabilite):
    combined_df = pd.DataFrame()

    # Accumuler les durées pour chaque fichier CSV
    for csv_file in datasets:
        df = pd.read_csv(csv_file)
        df_avg = df.groupby('Nworkers')['Time'].mean().reset_index()
        if combined_df.empty:
            combined_df = df_avg
        else:
            combined_df = combined_df.merge(df_avg, on='Nworkers', suffixes=('', '_dup'))

    # Calculer la moyenne des temps
    time_columns = [col for col in combined_df.columns if 'Time' in col]
    combined_df['AverageTime'] = combined_df[time_columns].mean(axis=1)

    # Calculer le speedup moyen
    t1 = combined_df['AverageTime'].iloc[0]
    combined_df['AverageSpeedup'] = t1 / combined_df['AverageTime']

    # Tracer les courbes
    if not avoidMultipleSp:
        if scalabilite == scale_up:
            ax.plot(combined_df['Nworkers'], combined_df['Nworkers'], linestyle='--', color='r',
                    label='Speedup linéaire (Sp=p)')
        elif scalabilite == scale_out:
            ax.plot(combined_df['Nworkers'], [1] * len(combined_df), linestyle='--', color='r',
                    label='Speedup linéaire (Sp=1)')
            ax.set_ylim(0, 1.4)
        else:
            print("Le paramètre 'scalabilite' est mal renseigné")

    ax.plot(combined_df['Nworkers'], combined_df['AverageSpeedup'], marker='o', linestyle='-',
            label=f'Speedup moyen de {method}')


def plot_all_speedups(ax, Dataset, scalabilite):
    for index, csv_file in enumerate(Dataset):
        avoid_multiple_sp = index != 0
        plot_speedup(ax, csv_file, avoid_multiple_sp, scalabilite)


def update_plots():
    ax1.clear()
    ax2.clear()
    if current_dataset == "myLaptop":
        if current_method == "moyenne":
            plot_average_speedup(ax1, myLaptop["pi"]["out"], "Pi", False, "out")
            plot_average_speedup(ax1, myLaptop["assignment102"]["out"], "Assignment102", True, "out")
            plot_average_speedup(ax1, myLaptop["master_worker"]["out"], "MasterWorker", True, "out")

            plot_average_speedup(ax2, myLaptop["pi"]["up"], "Pi", False, "up")
            plot_average_speedup(ax2, myLaptop["assignment102"]["up"], "Assignment102", True, "up")
            plot_average_speedup(ax2, myLaptop["master_worker"]["up"], "MasterWorker", True, "up")
        else:
            plot_all_speedups(ax1, myLaptop[current_method]["out"], "out")
            plot_all_speedups(ax2, myLaptop[current_method]["up"], "up")
    elif current_dataset == "G26D4":
        if current_method == "moyenne":
            plot_average_speedup(ax1, G26D4["pi"]["out"], "Pi", False, "out")
            plot_average_speedup(ax1, G26D4["assignment102"]["out"], "Assignment102", True, "out")
            plot_average_speedup(ax1, G26D4["master_worker"]["out"], "MasterWorker", True, "out")

            plot_average_speedup(ax2, G26D4["pi"]["up"], "Pi", False, "up")
            plot_average_speedup(ax2, G26D4["assignment102"]["up"], "Assignment102", True, "up")
            plot_average_speedup(ax2, G26D4["master_worker"]["up"], "MasterWorker", True, "up")
        else:
            plot_all_speedups(ax1, G26D4[current_method]["out"], "out")
            plot_all_speedups(ax2, G26D4[current_method]["up"], "up")
    ax1.set_xlabel("Nombre de coeurs")
    ax1.set_ylabel("Speedup")
    ax1.set_title(f"Scalabilité faible {current_method} ({current_dataset})")
    ax1.legend()
    ax1.grid()
    ax2.set_xlabel("Nombre de coeurs")
    ax2.set_ylabel("Speedup")
    ax2.set_title(f"Scalabilité forte {current_method} ({current_dataset})")
    ax2.legend()
    ax2.grid()
    plt.draw()


def set_dataset(event, dataset):
    global current_dataset
    current_dataset = dataset
    update_plots()


def set_method(event, method):
    global current_method
    current_method = method
    update_plots()


# Configuration initiale
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 6))
plt.subplots_adjust(bottom=0.3)

# Configuration des boutons
button_width = 0.18
button_height = 0.075

# Boutons pour sélectionner le dataset
ax_button_myLaptop = plt.axes([0.3, 0.15, button_width, button_height])
ax_button_G26D4 = plt.axes([0.5, 0.15, button_width, button_height])
ax_button_pi = plt.axes([0.1, 0.05, button_width, button_height])
ax_button_assignment102 = plt.axes([0.3, 0.05, button_width, button_height])
ax_button_master_worker = plt.axes([0.5, 0.05, button_width, button_height])
ax_button_compare = plt.axes([0.7, 0.05, button_width, button_height])

# Boutons pour sélectionner la méthode
btn_myLaptop = Button(ax_button_myLaptop, 'myLaptop', color="#7eb9fc")
btn_G26D4 = Button(ax_button_G26D4, 'G26D4', color="#7eb9fc")
btn_pi = Button(ax_button_pi, 'Pi', color='#fc7e7e')
btn_assignment102 = Button(ax_button_assignment102, 'Assignment102', color='#fc7e7e')
btn_master_worker = Button(ax_button_master_worker, 'MasterWorker', color='#fc7e7e')
btn_compare = Button(ax_button_compare, 'Comparer les 3 (moyennes des datas)', color='#95fc7e')

# Associer les fonctions aux boutons
btn_myLaptop.on_clicked(lambda event: set_dataset(event, "myLaptop"))
btn_G26D4.on_clicked(lambda event: set_dataset(event, "G26D4"))
btn_pi.on_clicked(lambda event: set_method(event, "pi"))
btn_assignment102.on_clicked(lambda event: set_method(event, "assignment102"))
btn_master_worker.on_clicked(lambda event: set_method(event, "master_worker"))
btn_compare.on_clicked(lambda event: set_method(event, "moyenne"))

# Afficher les graphes par défaut
update_plots()

plt.show()
