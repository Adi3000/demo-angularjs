whichOnes.config(function ($translateProvider) {
	$translateProvider.translations('en', {
		MSG_DELETE_CONFIRM: 'Are you sure to delete this line ?',
		MSG_MANDATORY: 'You need to specify a value for this field',
		HP_NEW_COLUMN:  'New column',
		LB_CREATE_SHEET: 'Create',
		LB_COLUMN_FILED: 'Column name',
		LB_SECTION_FILED: 'Section name',
		LB_SHEET_DESCRIPTION: 'Description',
		LB_SHEET_TITLE: 'Title'
	});
	$translateProvider.translations('fr', {
		MSG_DELETE_CONFIRM: 'Êtes-vous sûr de vouloir supprimer cette ligne ?',
		MSG_MANDATORY: 'Vous devez spécifier une valeur pour ce champ',
		HP_NEW_COLUMN:  'Nouvelle colonne',
		LB_CREATE_SHEET: 'Créer',
		LB_COLUMN_FILED: 'Nom de colonne',
		LB_SECTION_FILED: 'Nom de section',
		LB_SHEET_DESCRIPTION: 'Description',
		LB_SHEET_TITLE: 'Titre'
	});
	$translateProvider.preferredLanguage('en');
});