context('Neo4j Movies app', () => {
    const defaultMovieTitles = [
        'The Matrix',
        'The Matrix Reloaded',
        'The Matrix Revolutions',
    ];

    beforeEach(() => {
        cy.visit('/')
    })

    it('displays the application title', () => {
        cy.get('.navbar-brand .brand').contains('Neo4j Movies')
    })

    it('displays default movie titles', () => {
        cy.get('td.movie').should((elements) => {
            expect(elements.toArray().map(e => e.innerText)).to.deep.equal(defaultMovieTitles);
        });
    })

    it('displays the crew of the default selected movie', () => {
        cy.get('#title').should('have.text', defaultMovieTitles[0]);
        cy.get('#crew li').should((elements) => {
            expect(elements.toArray().map(e => e.innerText).sort()).to.deep.equal([
                'Carrie-Anne Moss acted as Trinity',
                'Emil Eifrem acted as Emil',
                'Hugo Weaving acted as Agent Smith',
                'Joel Silver produced',
                'Keanu Reeves acted as Neo',
                'Lana Wachowski directed',
                'Laurence Fishburne acted as Morpheus',
                'Lilly Wachowski directed'
            ]);
        });
    })

    it('displays the crew of the newly selected movie', () => {
        const secondElement = 1;
        cy.get('td.movie')
            .eq(secondElement)
            .click();

        cy.get('#title').should('have.text', defaultMovieTitles[secondElement]);
        cy.get('#crew li').should((elements) => {
            expect(elements.toArray().map(e => e.innerText).sort()).to.deep.equal([
                'Carrie-Anne Moss acted as Trinity',
                'Hugo Weaving acted as Agent Smith',
                'Joel Silver produced',
                'Keanu Reeves acted as Neo',
                'Lana Wachowski directed',
                'Laurence Fishburne acted as Morpheus',
                'Lilly Wachowski directed'
            ]);
        });
    })

    it('finds results matching the search', () => {
        const search = 'The';
        cy.get('#search input[type=text]')
            .clear()
            .type(search + '{enter}')

        cy.get('td.movie').should((movies) => {
            expect(movies.length).to.be.greaterThan(0)
            movies.toArray()
                .forEach((movie) => {
                    expect(movie.innerText).to.match(new RegExp(search, "i"));
                });
        });
    })
});
